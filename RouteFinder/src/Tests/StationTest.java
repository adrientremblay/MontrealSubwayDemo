package Tests;

import org.junit.jupiter.api.Test;
import tutorial_03_soen343.Station;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    private final String TEST_STATION_NAME1 = "Cedric's House";
    private final String TEST_STATION_NAME2 = "Steve's House";

    @Test
    void testEquals() {
        Station station1 = new Station(TEST_STATION_NAME1);
        Station station2 = new Station(TEST_STATION_NAME1);
        Station station3 = new Station(TEST_STATION_NAME2);

        assertTrue(station1.equals(station2));
        assertFalse(station1.equals(station3));
    }
}