package tutorial_03_soen343;

import java.io.*;
import java.util.*;

public class SubwayPrinter {
    private PrintStream console;
    private PrintStream file;
    private final int PRICE_PER_STATION = 1; // $cad
    
    public SubwayPrinter(OutputStream console) throws FileNotFoundException {
        this.console = new PrintStream(console);
        this.file = new PrintStream(new File("output.txt"));
    }
    private void println(String output) {
        this.console.println(output);
        this.file.println(output);
    }
    
    public void printDirections(List route) {
        Connection connection = (Connection) route.get(0);
        String currentLine = connection.getLineName();
        String previousLine = currentLine;
        
        println("Start out at " + connection.getStation1().getName() + ".");
        println("Get on the " + currentLine + " heading towards " + connection.getStation2().getName() + ".");
        
        for (int i = 1; i < route.size(); i++) {
            connection = (Connection) route.get(i);
            currentLine = connection.getLineName();
            if (currentLine.equals(previousLine)) {
                println("  Continue past  " + connection.getStation1().getName() + "...");
            }
            else {
                println("When you get to " + connection.getStation1().getName() + ", get off the " + previousLine + ".");
                println("Switch over to the " + currentLine + ", heading towards " + connection.getStation2().getName() + ".");
                previousLine = currentLine;
            }
        }
        println("Get off at " + connection.getStation2().getName() + " and enjoy yourself!");

        // printing price
        int price = PRICE_PER_STATION * route.size() + 1;
        println("Total cost will be " + price + "$");
    }
}
