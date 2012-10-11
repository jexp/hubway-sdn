/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.hubway.core;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
@TypeAlias("Station")
public class Station extends AbstractEntity {
    @Indexed(numeric = false)
    private Short stationId;
    @Indexed(unique=true)
	private String terminalName;

    @Indexed(indexType = IndexType.FULLTEXT, indexName = "stations")
    private String name;

    boolean installed, locked, temporary;

    double lat, lon;
    @Indexed(indexType = IndexType.POINT, indexName = "locations")
    String wkt;

    protected Station() {
    }

    public Station(Short stationId, String terminalName, String name, double lat, double lon) {
        this.stationId = stationId;
        this.name = name;
        this.terminalName = terminalName;
        this.lon = lon;
        this.lat = lat;
        this.wkt = String.format("POINT(%f %f)",lon,lat).replace(",",".");
    }

    public String getTerminalName() {
        return terminalName;
    }

    public String getName() {
        return name;
    }

    public Short getStationId() {
        return stationId;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s",stationId,name);
    }
}
