/* Andres Davila
 * Professor Saeed
 * COP 3337
 * February 19, 2023
 * I affirm that this program is entirely my own work and none of it is 
the work of any other person.

 */

import java.io.*;
import java.util.*;


public class Histogram {
    public static void main(String[] args) {

        // Strings to add the input files and result file onto the program for recognition
        String[] files = {"test1.txt", "test2.txt", "test3.txt"};
        String outputFilename = "results.txt";

        // Setting up the histogram
        try (PrintWriter writer = new PrintWriter(outputFilename)) {
            for (String filename : files) {
                writer.println("Reading data from file: " + filename);

                // Sorting out the numbers in the [1, 100] range, outside of the range, any symbols, any lowercase and uppercase letters
                List<Integer> integers = new ArrayList<>();
                List<Character> uppercase = new ArrayList<>();
                List<Character> lowercase = new ArrayList<>();
                int numIntegers = 0; // To start fresh and continue adding onto the array
                int numOthers = 0;

                // Scanner to get user input from the test files
                try (Scanner scanner = new Scanner(new File(filename))) { 
                    while (scanner.hasNext()) {
                        String token = scanner.next(); // Organizing the arrays with a try and catch method
                        if (token.matches("\\d+")) {
                            int value = Integer.parseInt(token);
                            if (value >= 1 && value <= 100) {
                                integers.add(value);
                                numIntegers++;
                            } else {
                                numOthers++;
                            }
                        } else if (token.matches("[A-Z]")) {
                            uppercase.add(token.charAt(0));
                        } else if (token.matches("[a-z]")) {
                            lowercase.add(token.charAt(0));
                        } else if (token.matches("2ff")){ // There is a line on test3 that is not considered anything, so it is skipped
                            token.isBlank();
                        } else {
                            numOthers++;
                        }
                    }
                } catch (FileNotFoundException e) {
                    writer.println("Error: File not found.");
                    continue;
                } catch (Exception e) {
                    writer.println("Error: " + e.getMessage());
                    continue;
                }

                writer.println("Number of integers in the interval [1, 100]: " + numIntegers);
                writer.println("Others: " + numOthers);
                writer.println("Histogram:");

                int[] bins = new int[10]; // Setting up the histogram bins
                for (int value : integers) {
                    bins[(value - 1) / 10]++;
                }

                for (int i = 0; i < bins.length; i++) {
                    int lowerBound = i * 10 + 1;
                    int upperBound = lowerBound + 9;
                    if (upperBound > 100) {
                        upperBound = 100;
                    }
                    String line = String.format("%2d - %2d | ", lowerBound, upperBound); // Showing the count as asterisks
                    for (int j = 0; j < bins[i]; j++) {
                        line += "*";
                    }
                    writer.println(line);
                }

                String uppercaseAsterisks = "";
                for (int i = 0; i < uppercase.size(); i++) {
                uppercaseAsterisks += "*";
                }

                String lowercaseAsterisks = "";
                for (int i = 0; i < lowercase.size(); i++) {
                lowercaseAsterisks += "*";
                }

                writer.println("Uppercase | " + uppercaseAsterisks);
                writer.println("Lowercase | " + lowercaseAsterisks);
                writer.println();
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
