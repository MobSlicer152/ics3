// Program to calculate average of some number of marks

// Import things from the standard library
import java.lang.Math;
import java.lang.System;

import java.util.Scanner;

// Main class
public class Main {

    // Entry point
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner reading from standard input

        // Prompt user for inputs (println makes a new line, but print doesn't, which is
        // good for input. The \n is so it matches the output on the page)
        System.out.println("Enter the percentage earned on each test.\n");

        // This is so I can be lazy and not write the same thing 6 times
        final int MARK_COUNT = 6;

        double allMarks = 0; // This holds the sum of all the marks
        double max = 0; // This holds the highest mark
        double min = 500.0; // This holds the lowest mark (initialized to 100 so any mark is lower for the first comparison)

        // Loop through marks
        for (int i = 0; i < MARK_COUNT; i++) {
            // printf is slightly more convenient for outputting variables in the message, such as i
            System.out.printf("What is student #%d's mark? ", i); // %d means decimal integer
            int mark = scanner.nextInt();

            // Add to all the marks
            allMarks += mark;

            // Update highest/lowest if necessary
            if (mark < min) {
                min = mark;
            }
            if (mark > max) {
                max = mark;
            }
        }

        // Get the average
        double average = allMarks / MARK_COUNT;

        // Output (newline at start is, again, to match output on page)
        System.out.printf("\nThere are %d students in the class.\n", MARK_COUNT);
        // printf uses % specially, so to output one, you have to "escape" it so it isn't interpretted specially
        System.out.printf("The average mark was %.2f %% (rounded up is %.2f %%, rounded down is %.2f %%).\n", average, Math.ceil(average), Math.floor(average));
        System.out.printf("The highest was %.2f %%, and the lowest was %.2f %%.\n", max, min);
    }
}
