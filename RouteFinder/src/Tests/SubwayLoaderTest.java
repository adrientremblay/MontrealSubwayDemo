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
            () -> assertEquals(true, city.hasStation("Guy Concordia")),
            () -> assertEquals(true, city.hasStation("Snowdon")),
            () -> assertEquals(true, city.hasStation("Berri UQAM"))
        );

        // testing connections
        assertAll(
            () -> assertEquals(true, city.hasConnection("Atwater", "Guy Concordia", "Green Line")),
            () -> assertEquals(true,  city.hasConnection("Cote Vertu", "Du College", "Orange Line")),
            () -> assertEquals(true, city.hasConnection("Snowdon", "Cote des Neiges", "Blue Line"))
        );
    }
}