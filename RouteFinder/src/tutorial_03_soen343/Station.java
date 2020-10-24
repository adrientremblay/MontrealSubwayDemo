package tutorial_03_soen343;

/**
 * Represents a station of the train network.
 * Stations can be compared to one another and
 * are considered equal if they have the same name.
 * Stations can be hashed according to their names.
 */
public class Station {
    private String name;

    /**
     * Creates a Station given a name value.
     * @param name the name of the station
     */
    public Station(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the station.
     * @return the name of the station
     */
    public String getName() {
        return name;
    }

    /**
     * Determines if the given station is equal to this station.
     * Stations are considered equal if they have the same name.
     * @param obj the Station to compare with this Station
     * @return true if Stations are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj instanceof Station) {
            Station otherStation = (Station) obj;
            if (otherStation.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
