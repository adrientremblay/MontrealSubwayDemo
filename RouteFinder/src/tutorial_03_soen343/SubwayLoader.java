package tutorial_03_soen343;

import java.io.File;
import java.io.*;

/**
 * This class provides the functionality to load a Subway from
 * a file of a particular format.
 * This format should first specify a list of
 * stations on a number of lines.
 * Followed by a blank line and then A number of stations
 * on new lines representing a station line.
 * Each of these should be preceded by the name of the
 * line.
 */
public class SubwayLoader {
    private Subway subway;

    /**
     * Constructs a new SubwayLoader.
     */
    public SubwayLoader() {
        this.subway = new Subway();
    }

    /**
     * Loads and returns a Subway from a given subwayFile.
     * This method triggers the loadStation and loadLine
     * methods.
     * @param subwayFile the file to be loaded and parsed
     * @return the constructed Subway
     * @throws IOException
     */
    public Subway loadFromFile(File subwayFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(subwayFile));
        loadStations(subway, reader);
        String lineName = reader.readLine();
        while ((lineName != null) && (lineName.length() > 0)) {
            loadLine(subway, reader, lineName);
            lineName = reader.readLine();
        }
        return subway;
    }

    /**
     * Loads Stations from the file ana add them to the
     * subway.
     * The BufferedReader reader is assumed to be in
     * the correct position to start reading stations.
     * @param subway the Subway to continue constructing
     * @param reader the reader to use to access the subway file
     * @throws IOException
     */
    private void loadStations(Subway subway, BufferedReader reader) throws IOException {
        String currentLine;
        currentLine = reader.readLine();
        while (currentLine.length() > 0) {
            subway.addStation(currentLine);
            currentLine = reader.readLine();
        }
    }

    /**
     * Loads a station line from the file, creating
     * connections and adding them to the subway.
     * The BufferedReader reader is assumed to be in
     * the correct position to start reading a station line.
     * @param subway the Subway to continue constructing
     * @param reader the reader to use to access the subway file
     * @param lineName the name of the line
     * @throws IOException
     */
    private void loadLine(Subway subway, BufferedReader reader, String lineName) throws IOException {
        String station1Name, station2Name;
        station1Name = reader.readLine();
        station2Name = reader.readLine();
        while ((station2Name != null) && (station2Name.length() > 0)) {
            subway.addConnection(station1Name, station2Name, lineName);
            station1Name = station2Name;
            station2Name = reader.readLine();
        }
    }
}
