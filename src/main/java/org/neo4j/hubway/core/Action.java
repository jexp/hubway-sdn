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
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import java.util.Date;

@RelationshipEntity(type = "ACTION")
@TypeAlias("Action")
public class Action extends AbstractEntity {

	@StartNode
	private Trip trip;
    @EndNode
	private Station station;

    @GraphProperty(propertyType = long.class) // todo better representation
    private Date date;

    public Action(Trip trip, Station station, Date date) {
        this.trip = trip;
        this.station = station;
        this.date = date;
    }

    protected Action() {
	}

    public Trip getTrip() {
        return trip;
    }

    public Station getStation() {
        return station;
    }

    public Date getDate() {
        return date;
    }
}
