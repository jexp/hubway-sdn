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

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Date;

@NodeEntity
@TypeAlias("Trip")
public class Trip extends AbstractEntity {

	@RelatedTo(direction = Direction.INCOMING,type="TRIP")
	private Member member;
	@RelatedToVia(type="END")
	private Action end;
	@RelatedToVia(type="START")
	private Action start;

    @RelatedTo(type="BIKE")
    private Bike bike;

    protected Trip() { }

    public Trip(Member member, Bike bike) {
        this.member = member;
        this.bike = bike;
    }

    public Trip to(Station end, Date date) {
        this.end = new Action(this,end,date);
        return this;
    }

    public Trip from(Station from, Date date) {
        this.start = new Action(this,from,date);
        return this;
    }

    public Member getMember() {
		return member;
	}

	public Action getEnd() {
		return end;
	}

	public Action getStart() {
		return start;
	}
	public Station getEndStation() {
		return end.getStation();
	}

	public Station getStartStation() {
		return start.getStation();
	}

    public Bike getBike() {
        return bike;
    }
}
