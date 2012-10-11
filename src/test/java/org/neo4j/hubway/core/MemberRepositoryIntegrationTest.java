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
import org.junit.Test;
import org.neo4j.hubway.TestApplicationConfig;
import org.neo4j.hubway.core.Gender;
import org.neo4j.hubway.core.Member;
import org.neo4j.hubway.core.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

public class MemberRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
    MemberRepository repository;

	@Test
	public void savesMemberCorrectly() {

        Member member = createMember();
		assertThat(member.getId(), is(notNullValue()));
	}

    private Member createMember() {
        Member alicia = new Member("Alicia", "1234", Gender.FEMALE,1900);

        return repository.save(alicia);
    }

    @Test
	public void readsCustomerByEmail() {

        Member alicia = createMember();
		Collection<Member> result = repository.findByZipCode("1234");
		assertThat(result, hasItem(alicia));
	}

	@Test
	public void preventsDuplicateEmail() {
        Member dave = repository.findByName("Dave");
        assertThat(dave, is(dave));

	}
}
