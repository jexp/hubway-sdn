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
package org.neo4j.hubway;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.neo4j.hubway.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestApplicationConfig.class })
@Transactional
public abstract class AbstractIntegrationTest {

	@Autowired
    protected Neo4jTemplate template;
    protected Bike bike;
    protected Station start;
    protected Station end;
    protected Member dave;
    protected Trip trip;

    @Before
    public void setUp() throws Exception {
        dave = template.save(new Member("Dave","02116", Gender.MALE,1976));
        start = template.save(new Station((short)1,"A32000", "Fan Pier",42.353412,-71.044624));
        end = template.save(new Station((short)2,"A32001","Union Square - Brighton Ave. at Cambridge St",42.353334,-71.137313));
        trip = new Trip(dave,bike).from(start,date("2012-01-01 12:00")).to(end,date("2012-01-01 14:00"));
        template.save(trip);
    }

    protected Date date(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
