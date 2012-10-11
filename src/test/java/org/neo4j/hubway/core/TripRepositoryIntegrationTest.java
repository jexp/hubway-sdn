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

import org.neo4j.hubway.AbstractIntegrationTest;
import org.neo4j.hubway.core.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class TripRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
    TripRepository repository;

	@Autowired
    MemberRepository memberRepository;
	@Autowired
    StationRepository stationRepository;
	@Autowired
    TripRepository tripRepository;

	@Test
	public void createTrip() {

		Member dave = memberRepository.findByName("Dave");

		Trip newTrip = new Trip(dave,bike).from(start,date("2012-12-31 23:00")).to(end,date("2013-01-01 01:00"));

		newTrip = repository.save(newTrip);
		assertThat(newTrip.getId(), is(notNullValue()));
	}

	@Test
	public void readTrip() {


		List<Trip> trips = repository.findByMember(dave);

		assertThat(trips.size(), is(1));

		assertThat(trips, hasItem(trip));
	}
}
