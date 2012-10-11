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

@NodeEntity
@TypeAlias("Member")
public class Member extends AbstractEntity {

    // todo we don't have that information !!
    @Indexed(unique = true)
	private String name;

    private String zipCode;

    private Gender gender;
    private Integer bornIn;

    public Member(String zipCode, Gender gender, Integer bornIn) {
        this(gender.toString()+"_"+bornIn+"_"+zipCode,
             zipCode,gender,bornIn);
    }
    public Member(String name, String zipCode, Gender gender, Integer bornIn) {
        this.name = name;
        this.zipCode = zipCode;
        this.gender = gender;
        this.bornIn = bornIn;
    }

    protected Member() {
	}

	public String getName() {
		return name;
	}

    public String getZipCode() {
        return zipCode;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getBornIn() {
        return bornIn;
    }

    @Override
    public String toString() {
        return name;
    }
}
