package tutorial_03_soen343;

import java.util.*;
import java.util.stream.Stream;

public class Subway {
    private List stations;
    private List connections;
    private Map network;
    
    public Subway() {
        this.stations = new LinkedList();
        this.connections = new LinkedList();
        this.network = new HashMap();
    }
    
    public void addStation(String stationName) {
        if (!this.hasStation(stationName)) {
            Station station = new Station(stationName);
            stations.add(station);
        }
    }
    
    public boolean hasStation(String stationName) {
        return this.getStation(stationName) != null;
    }

    public Station getStation(String stationName) {
        Stream<Station>  stationStream = this.stations.stream();
        Optional<Station> foundStation = stationStream.filter(station -> station.getName().equals( stationName)).findFirst();
        if (foundStation.isPresent())
            return foundStation.get();

        return null;
    }
    
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

        // special case where end state is one connection away from starting station
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

        // TODO: Turn this into a do while loop
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
