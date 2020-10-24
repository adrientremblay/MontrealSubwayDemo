package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorial_03_soen343.Station;
import tutorial_03_soen343.Subway;

import static org.junit.jupiter.api.Assertions.*;

class SubwayTest {
    private final String[][] TEST_LINES = {
            {"A", "B", "C", "D", "E", "F"},
            {"G", "H", "I", "D", "J", "K"},
            {"L", "B", "M", "N", "J", "P"},
    };

    private Subway subway;

    @Test
    void getDirections() {
        subway = new Subway();
        // building subway network
        for (int i = 0 ; i < TEST_LINES.length ; i ++) {
            String lineName = "Line #" + i;
            // add all stations first
            for (int j = 0 ; j < TEST_LINES[i].length ; j++) {
                subway.addStation(TEST_LINES[i][j]);
            }
            // add connections
            for (int j = 0 ; j < TEST_LINES[i].length - 1 ; j++) {
                subway.addConnection(TEST_LINES[i][j], TEST_LINES[i][j+1], lineName);
            }
        }

        assertEquals(
                "[[A, B, Line #0], [B, C, Line #0], [C, D, Line #0], [D, J, Line #1], [J, P, Line #2]]",
                subway.getDirections("A", "P").toString()
        );
    }

}