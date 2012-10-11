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

import org.junit.Test;
import org.neo4j.hubway.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class StationRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
    StationRepository repository;

	@Test
	public void createStation() {
		Station station = new Station((short)1,"A1", "Home sweet home",12,20);
		Station result = repository.save(station);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getName(), is(station.getName()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void lookupStationsByDescription() {

		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "station.name");
		Page<Station> page = repository.findByNameLike("Square", pageable);

		assertThat(page.getContent().size(), is(1));
        Station station = page.getContent().get(0);
        assertThat(station.getId(), is(end.getId()));
		assertThat(station.getTerminalName(), is(end.getTerminalName()));
		assertThat(page.isFirstPage(), is(true));
	}
}
