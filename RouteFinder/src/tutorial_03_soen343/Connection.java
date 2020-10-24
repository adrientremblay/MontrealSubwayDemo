package tutorial_03_soen343;

/**
 * Represents a Connection between two stations on a given line.
 * A single connection indicates to that is is possible to travel
 * from station1 to station2.
 * Connections are created in pairs so that it is understood
 * that trains can travel from station1 to station2 and from
 * station2 to station1.
 */
public class Connection {
    private Station station1, station2;
    private String lineName;

    /**
     * Creates a Connection.
     * @param station1 the station to connecting from
     * @param station2 the station connecting to
     * @param lineName the name of the line the connection is a member of
     */
    public Connection(Station station1, Station station2, String lineName) {
        this.station1 = station1;
        this.station2 = station2;
        this.lineName = lineName;
    }

    /**
     * Gets the station connecting from. ie. station1.
     * @return the Station object representing station1
     */
    public Station getStation1() {
        return station1;
    }

    /**
     * Gets the station connecting to. ie. station2.
     * @return the Station object representing station2
     */
    public Station getStation2() {
        return station2;
    }

    /**
     * Gets the name of the line the connection is a part of.
     * @return a string representing the line name
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * Converts the connection to a String representation including
     * the names of station1, station2, and the line.
     * @return a string representation of the connection
     */
    public String toString() {
        return "[" + station1.getName() + ", " + station2.getName() + ", " + lineName + "]";
    }

}
