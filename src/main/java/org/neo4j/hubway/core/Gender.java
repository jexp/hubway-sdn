package org.neo4j.hubway.core;

public enum Gender { MALE, FEMALE;

    public static Gender from(String gender) {
        if (gender == null) return null;
        if (gender.equalsIgnoreCase("male")) return MALE;
        if (gender.equalsIgnoreCase("female")) return FEMALE;
        return null;
    }
}
