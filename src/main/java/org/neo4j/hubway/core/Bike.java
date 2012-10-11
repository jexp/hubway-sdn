package org.neo4j.hubway.core;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
@TypeAlias("Bike")
public class Bike extends AbstractEntity {
    @Indexed(unique = true)
    String bikeId;

    protected Bike() {
    }

    public Bike(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeId() {
        return bikeId;
    }

    @Override
    public String toString() {
        return bikeId;
    }
}
