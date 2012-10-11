package org.neo4j.hubway;

import com.csvreader.CsvReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.hubway.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImportService {

    private static final Log log= LogFactory.getLog(ImportService.class);

    @Autowired private Neo4jTemplate template;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");

    private final Map<Short,Station> stations = new HashMap<Short, Station>();
    private final Map<String,Bike> bikes = new HashMap<String, Bike>();
    private final Map<String,Member> members = new HashMap<String, Member>();

    @Transactional
    public void importStations(CsvReader stationsFile) throws IOException {
        // id,terminalName,name,installed,locked,temporary,lat,lng
        while (stationsFile.readRecord()) {
            System.out.println(Arrays.toString(stationsFile.getValues()));
            Station station = new Station(asShort(stationsFile,"id"),stationsFile.get("terminalName"), stationsFile.get("name"), asDouble(stationsFile, "lat"), asDouble(stationsFile, "lng"));
            template.save(station);
            stations.put(station.getStationId(), station);
        }
    }


    @Transactional
    public boolean importTrips(CsvReader trips, int count) throws IOException {
        //"id","status","duration","start_date","start_station_id","end_date","end_station_id","bike_nr","subscription_type","zip_code","birth_date","gender"
        while (trips.readRecord()) {
            Station start = findStation(trips, "start_station_id");
            Station end = findStation(trips, "end_station_id");
            if (start==null || end==null) {
                if (log.isInfoEnabled()) log.info("Skipping "+ Arrays.toString(trips.getValues()));
                continue;
            }

            Member member = obtainMember(trips);

            Bike bike = obtainBike(trips);

            Trip trip = new Trip(member, bike).from(start, date(trips.get("start_date"))).to(end, date(trips.get("end_date")));
            template.save(trip);
            count--;
            if (count==0) return true;
        }
        return false;
    }

    private Station findStation(CsvReader trips, String column) throws IOException {
        Short id = asShort(trips, column);
        if (id==null) return null;
        Station station = stations.get(id);
        if (station==null) if (log.isInfoEnabled()) log.info("No station found for id: "+trips.get(column));
        return station;
    }

    @Transactional
    private Member obtainMember(CsvReader trips) throws IOException {
        boolean registered = trips.get("subscription_type").equals("Registered");
        if (!registered) return null;
        Member lookup = new Member(trips.get("zip_code"), Gender.from(trips.get("gender")), asInt(trips, "birth_date"));
        Member member = members.get(lookup.getName());
        if (member==null) {
            member = template.save(lookup);
            members.put(member.getName(),member);
        }
        return member;
    }

    @Transactional
    private Bike obtainBike(CsvReader trips) throws IOException {
        Bike bike = bikes.get(trips.get("bike_nr"));
        if (bike==null) {
            bike = template.save(new Bike(trips.get("bike_nr")));
            bikes.put(bike.getBikeId(),bike);
        }
        return bike;
    }


    private Double asDouble(CsvReader csv, String column) throws IOException {
        String value = csv.get(column);
        if (value==null || value.trim().isEmpty()) return null;
        return Double.valueOf(value);
    }


    private Integer asInt(CsvReader csv, String column) throws IOException {
        String value = csv.get(column);
        if (value==null || value.trim().isEmpty()) return null;
        return Integer.valueOf(value);
    }
    private Short asShort(CsvReader csv, String column) throws IOException {
        String value = csv.get(column);
        if (value==null || value.trim().isEmpty()) return null;
        return Short.valueOf(value);
    }

    private Date date(String date) {
        try {
            return SIMPLE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            if (log.isInfoEnabled()) log.info(e.getMessage());
            return null;
        }
    }
}
