package tutorial_03_soen343;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a subway system.
 * Contains a list of stations (stations), connections between these stations (connections),
 * as well as a HashMap of every station and the stations (network).
 * they connect to.
 */
public class Subway {
    private List stations;
    private List connections;
    private Map network;

    /**
     * Creates a subway system.
     * Initializes attributes.
     */
    public Subway() {
        this.stations = new LinkedList();
        this.connections = new LinkedList();
        this.network = new HashMap();
    }

    /**
     * Adds a station to the subway given it's name.
     * The station is created only if it does not already exist
     * within the current subway.
     * @param stationName the name of the station to be added
     */
    public void addStation(String stationName) {
        if (!this.hasStation(stationName)) {
            Station station = new Station(stationName);
            stations.add(station);
        }
    }

    /**
     * Checks if a station of the given name already exists
     * within the subway.
     * @param stationName the name of the station to check for
     * @return true if the subway has already added this station, false otherwise
     */
    public boolean hasStation(String stationName) {
        return this.getStation(stationName) != null;
    }

    /**
     * Gets the Station with the given name.
     * Returns null if the station does not exist.
     * @param stationName the name of the station to get
     * @return the Station matching stationName
     */
    public Station getStation(String stationName) {
        Stream<Station>  stationStream = this.stations.stream();
        Optional<Station> foundStation = stationStream.filter(station -> station.getName().equals( stationName)).findFirst();
        if (foundStation.isPresent())
            return foundStation.get();

        return null;
    }

    /**
     * Records the connection between the two stations
     * specified by name.
     * If the stations are not present in the network
     * an exception is thrown.
     * A Connection object between station1 and station2 and
     * a Connection object between station2 and station1 is
     * created to record that one can travel from either station
     * to the other.
     * This method triggers the addToNetwork method which
     * updates the network HashMap to account for
     * this new connection between station1 and station2.
     *
     * @param station1Name the name of the first station
     * @param station2Name the name of the second station
     * @param lineName the name of the line this connection is on
     */
    public void addConnection(String station1Name, String station2Name, String lineName) {
        Station station1 = this.getStation(station1Name);
        Station station2 = this.getStation(station2Name);

        if (station1 == null || station2 == null) {
            throw new RuntimeException("Invalid connection: [" + station1Name + ", " + station2Name + ", " + lineName + "]");
        }

        // two connections with station1 and station2 switch to represent
        // being able to move back and forth from these two stations
        connections.add(new Connection(station1, station2, lineName));
        connections.add(new Connection(station2, station1, lineName));

        addToNetwork(station1, station2);
        addToNetwork(station2, station1);
    }

    /**
     * Updates the network HashMap to account for a new connection
     * from station1 and station2.
     * It should be recorded in network that station2 is reachable from
     * station1.
     * The keys of network are the hashed values of every Station,
     * the values of network are link lists representing all Stations
     * accessable from the key Station.
     * @param station1 the Station in question
     * @param station2 the Station that is to be recorded as accessible from station1
     */
    private void addToNetwork(Station station1, Station station2) {
        if (network.keySet().contains(station1)) {
            List connectingStations = (List) network.get(station1);
            if (!connectingStations.contains(station2)) {
                connectingStations.add(station2);
            }
        } else {
            List connectingStations = new LinkedList();
            connectingStations.add(station2);
            network.put(station1, connectingStations);
        }
    }

    /**
     * Uses Dijkstra's algorithm to generate the shortest path of travel
     * between stations given by startStationName and endStationName.
     * @param startStationName the name of the station to start travel from
     * @param endStationName the name of the station that ends travel
     * @return A list of the connections representing the shortest
     * travel route between  the stations represented by
     * startStationName and endStationName
     */
    public List getDirections(String startStationName, String endStationName) {
        if (!this.hasStation(startStationName) || !this.hasStation(endStationName)) {
            throw new RuntimeException("Stations entered do not exist on this subway");
        }


        // output vars
        Station start = this.getStation(startStationName);
        Station end = this.getStation(endStationName);
        List route = new LinkedList();

        // dijkstra vars
        List reachableStations = new LinkedList();
        Map previousStations = new HashMap();

        // special case where start station is already the end station
        if (startStationName.equals(endStationName)) {
            route.add(new Connection(start, end, null));
            return route;
        }

        // special case where end station is one connection away from starting station
        List neighbors = (List)network.get(start);
        for (Iterator i = neighbors.iterator(); i.hasNext(); ) {
            Station station = (Station) i.next();
            if (station.equals(end)) {
                route.add(getConnection(start, end));
                return route;
            } else {
                reachableStations.add(station);
                previousStations.put(station, start);
            }
        }
        
        List nextStations = new LinkedList();
        nextStations.addAll(neighbors);
        Station currentStation = start;
        
        searchLoop:
        for (int i = 1; i < stations.size(); i++) {
            List tmpNextStations = new LinkedList();
            for (Iterator j = nextStations.iterator(); j.hasNext(); ) {
                Station station = (Station) j.next();
                reachableStations.add(station);
                currentStation = station;
                List currentNeighbors = (List) network.get(currentStation);
                for (Iterator k = currentNeighbors.iterator(); k.hasNext(); ) {
                    Station neighbor = (Station) k.next();
                    if (neighbor.equals(end)) {
                        reachableStations.add(neighbor);
                        previousStations.put(neighbor, currentStation);
                        break searchLoop;
                    } else if (!reachableStations.contains(neighbor)) {
                        reachableStations.add(neighbor);
                        tmpNextStations.add(neighbor);
                        previousStations.put(neighbor, currentStation);
                    }
                }
            }
            nextStations = tmpNextStations;
        }
        
        // We've found the path now!
        // unwinding the path to create a list of connections to get from the starting station
        // to the destination station
        boolean keepLooping = true;
        Station keyStation = end;
        Station station;

        while (keepLooping) {
            station = (Station) previousStations.get(keyStation);
            route.add(0, getConnection(station, keyStation));
            if (start.equals(station)) {
                keepLooping = false;
            }
            keyStation = station;
        }
        
        return route;
    }

    /**
     * Gets the Connection object of station1 and station2.
     * Returns null if this Connection does not exist.
     * @param station1 the first given station
     * @param station2 the second given station
     * @return the Connection between station1 and station2 if it exists,
     * null otherwise
     */
    private Connection getConnection(Station station1, Station station2) {
        for (Iterator i = connections.iterator(); i.hasNext(); ) {
            Connection connection = (Connection) i.next();
            Station one = connection.getStation1();
            Station two = connection.getStation2();
            if ((station1.equals(one)) && station2.equals(two)) {
                return connection;
            }
        }
        return null;
    }

    /**
     * Determines if there exists a Connection between stations
     * of the names station1Name and station2Name on the line
     * represented by lineName.
     * @param station1Name the name of the first station
     * @param station2Name the naame of the second station
     * @param lineName the name of the line
     * @return true if a connection of the given parameters exists, false otherwise
     */
    public boolean hasConnection(String station1Name, String station2Name, String lineName) {
        Station station1 = new Station(station1Name);
        Station station2 = new Station(station2Name);
        for (Iterator i = connections.iterator(); i.hasNext(); ) {
            Connection connection = (Connection) i.next();
            if (connection.getLineName().equalsIgnoreCase(lineName)) {
                if ((connection.getStation1().equals(station1)) &&
                    (connection.getStation2().equals(station2)))
                {
                    return true;
                }
            }
        }
        return false;
    }
                
}
