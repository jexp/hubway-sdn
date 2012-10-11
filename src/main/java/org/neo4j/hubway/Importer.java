package org.neo4j.hubway;

import com.csvreader.CsvReader;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

public class Importer {

    public static void main(String[] args) throws IOException {
        FileUtils.deleteRecursively(new File("hubway.db"));
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:META-INF/spring/application-context.xml");
        try {
            new Importer().importData(ctx);
        } finally {
            ctx.close();
        }
    }

    private void importData(ClassPathXmlApplicationContext ctx) throws IOException {
        ImportService importer = ctx.getBean(ImportService.class);
        importStations(importer, "stations_trips/stations.csv");
        importTrips(importer, "stations_trips/trips.csv");
    }

    private void importStations(ImportService importer, String stationsCsv) throws IOException {
        CsvReader stationsFile = new CsvReader(stationsCsv);
        stationsFile.readHeaders();
        importer.importStations(stationsFile);
        stationsFile.close();
    }

    private void importTrips(ImportService importer, String tripsCsv) throws IOException {
        CsvReader trips = new CsvReader(tripsCsv);
        trips.readHeaders();
        int batches=0;
        long time=System.currentTimeMillis();
        while (importer.importTrips(trips, 5000)) {
            System.out.print(".");
            batches++;
            if (batches % 10 == 0) {
                System.out.println(" "+(System.currentTimeMillis()-time));
                time=System.currentTimeMillis();
            }
        }
        trips.close();
    }
}
