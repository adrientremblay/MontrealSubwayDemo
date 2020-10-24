package tutorial_03_soen343;

import java.io.*;
import java.util.*;

public class SubwayPrinter {
    private PrintStream out;
    private final int PRICE_PER_STATION = 1; // $cad
    
    public SubwayPrinter(OutputStream out) {
        this.out = new PrintStream(out);
    }
    
    public void printDirections(List route) {
        Connection connection = (Connection) route.get(0);
        String currentLine = connection.getLineName();
        String previousLine = currentLine;
        
        out.println("Start out at " + connection.getStation1().getName() + ".");
        out.println("Get on the " + currentLine + " heading towards " + connection.getStation2().getName() + ".");
        
        for (int i = 1; i < route.size(); i++) {
            connection = (Connection) route.get(i);
            currentLine = connection.getLineName();
            if (currentLine.equals(previousLine)) {
                out.println("  Continue past  " + connection.getStation1().getName() + "...");
            }
            else {
                out.println("When you get to " + connection.getStation1().getName() + ", get off the " + previousLine + ".");
                out.println("Switch over to the " + currentLine + ", heading towards " + connection.getStation2().getName() + ".");
                previousLine = currentLine;
            }
        }
        out.println("Get off at " + connection.getStation2().getName() + " and enjoy yourself!");

        // printing price
        int price = PRICE_PER_STATION * route.size() + 1;
        out.println("Total cost will be " + price + "$");
    }
}
