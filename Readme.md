Hubway cycle trip data import into Neo4j using Spring Data Neo4j

!! Modeling

    (Bike)<-[:bike]-(Trip)-[:member]->(Member)
    (Station)<-[:start]-(Trip)-[:end]->(Station)

!! Source

http://hubwaydatachallenge.org/trip-history-data/