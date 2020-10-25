package tutorial_03_soen343;

import java.io.*;
import java.util.*;

/**
 * Provides the functionality to convert
 * a list of connections representing the path taken by
 * the train from start to destination into a sequence
 * of steps and facts in plain english.
 * The cost of the journey is also calculated and
 * displayed.
 * These lines are printed to the given OutputStream and additionally
 * saved in an output file.
 */
public class SubwayPrinter {
    private PrintStream out;
    private PrintStream file;
    private final int PRICE_PER_STATION = 1; // $cad

    /**
     * Creates a SubwayPrinter using a given OutputStream.
     * @param out the OutputStream to print each line to
     * @throws FileNotFoundException
     */
    public SubwayPrinter(OutputStream out) throws FileNotFoundException {
        this.out = new PrintStream(out);
        this.file = new PrintStream(new File("output.txt"));
    }

    /**
     * Prints the given String to both the out OutputStream and
     * to the output file.
     * @param output the line String to print
     */
    private void println(String output) {
        this.out.println(output);
        this.file.println(output);
    }

    /**
     * Prints to the output file and given Output Stream
     * a series of actions and facts derived from
     * the List of connections given that represents the
     * travel from start to destination station.
     * @param route the list of connections taken
     */
    public void printDirections(List route) {
        int price = PRICE_PER_STATION * route.size() + 1;
        Connection connection = (Connection) route.get(0);
        String currentLine = connection.getLineName();
        String previousLine = currentLine;

        println("Start out at " + connection.getStation1().getName() + ".");

        // case where start and end stations are the same
        if (connection.getStation1().equals(connection.getStation2())) {
            price -= PRICE_PER_STATION;
        } else {
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

        }
        println("Get off at " + connection.getStation2().getName() + " and enjoy yourself!");

        println("Total cost will be " + price + "$");
    }
}
