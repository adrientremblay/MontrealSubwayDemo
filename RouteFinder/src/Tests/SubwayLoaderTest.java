package Tests;

import tutorial_03_soen343.Subway;
import tutorial_03_soen343.SubwayLoader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class SubwayLoaderTest {

    @org.junit.jupiter.api.Test
    void loadFromFile() throws IOException {
        SubwayLoader loader = new SubwayLoader();
        String subwayFilePath = "../MontrealSubway.txt";
        Subway city = loader.loadFromFile(new File(subwayFilePath));

        // testing stations loaded
        assertAll(
            () -> assertTrue(city.hasStation("Guy Concordia")),
            () -> assertTrue(city.hasStation("Snowdon")),
            () -> assertTrue(city.hasStation("Berri UQAM"))
        );

        // testing connections
        assertAll(
            () -> assertTrue(city.hasConnection("Atwater", "Guy Concordia", "Green Line")),
            () -> assertTrue(city.hasConnection("Cote Vertu", "Du College", "Orange Line")),
            () -> assertTrue(city.hasConnection("Snowdon", "Cote des Neiges", "Blue Line"))
        );
    }
}