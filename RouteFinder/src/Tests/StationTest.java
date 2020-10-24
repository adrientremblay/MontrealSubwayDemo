package Tests;

import org.junit.jupiter.api.Test;
import tutorial_03_soen343.Station;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    private final String TEST_STATION_NAME = "Cedric's House";

    @Test
    void testEquals() {
        Station station1 = new Station(TEST_STATION_NAME);
        Station station2 = new Station(TEST_STATION_NAME);

        assertEquals(true, station1.equals(station2));
    }
}