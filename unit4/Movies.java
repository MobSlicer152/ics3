import java.lang.System;

import java.util.Scanner;

public class Movies {
    public static void main(String[] args) {
        // Lengths
        final int NORMAL_LENGTH_MIN = 90;
        final int NORMAL_LENGTH_MAX = 180;

        // Movies
	final String[] MOVIE_TITLES = {
	    "Office Space",
	    "Frankenstein (1931)",
	    "Seven Samurai",
	    "The Avengers",
	    "Morbius",
	    "Hamlet (1996)",
	    "Titanic",
	    "The Godfather: Part II",
	    "Star Trek (2009)",
	    "Shrek",
	    "Shark Tale",
	    "King Kong (2005)",
	    "Gone with the Wind",
	    "Cats (2019)",
	    "Fantastic Mr. Fox"
	};
	final int[] MOVIE_LENGTHS = {
	    90,
	    75,
	    207,
	    143,
	    104,
	    242,
	    194,
	    210,
	    127,
	    90,
	    90,
	    201,
	    238,
	    110,
	    87
	};
	final int MOVIE_COUNT = MOVIE_TITLES.length;
    
        // Keep track of what movies are allowed in this array
        boolean[] allowed = new boolean[MOVIE_COUNT];
        // This is for recreating them
        int allowedCount = 0;
        for (int i = 0; i < MOVIE_COUNT - 1; i++) {
            if (MOVIE_LENGTHS[i] >= NORMAL_LENGTH_MAX &&
                    MOVIE_LENGTHS[i + 1] >= NORMAL_LENGTH_MAX) {
		allowed[i] = true;
		allowedCount++;
                allowed[i + 1] = false;
            } else if (MOVIE_LENGTHS[i] <= NORMAL_LENGTH_MIN &&
                    MOVIE_LENGTHS[i + 1] <= NORMAL_LENGTH_MIN) {
		allowed[i] = true;
		allowedCount++;
                allowed[i + 1] = false;
            } else {
                allowed[i] = true;
                allowedCount++;
            }
        }

        // New arrays
        String[] allowedMovieTitles = new String[allowedCount];
        int[] allowedMovieLengths = new int[allowedCount];
        int j = 0; // Index in the new arrays, incremented each allowed movie

        // Two loops to print too long and too short, none gets set
        // to false the first time a movie isn't allowed each loop
        // to know if "None" should be printed for that section.
        // Only the first loop needs to copy movies into the new arrays.
        
        boolean none = true;
        System.out.println("TOO LONG:");
        for (int i = 0; i < MOVIE_COUNT; i++) {
            if (!allowed[i] && MOVIE_LENGTHS[i] >= NORMAL_LENGTH_MAX) {
                System.out.println(MOVIE_TITLES[i] + " (" + MOVIE_LENGTHS[i] + " minutes)");
                none = false;
            } else if (allowed[i]) {
                allowedMovieTitles[j] = MOVIE_TITLES[i];
                allowedMovieLengths[j] = MOVIE_LENGTHS[i];
                j++;
            }
        }
        if (none) {
            System.out.println("None");
        }

        System.out.println("TOO SHORT:");
        none = true;
        for (int i = 0; i < MOVIE_COUNT; i++) {
            if (!allowed[i] && MOVIE_LENGTHS[i] <= NORMAL_LENGTH_MIN) {
                System.out.println(MOVIE_TITLES[i] + " (" + MOVIE_LENGTHS[i] + " minutes)");
                none = false;
            }
        }
        if (none) {
            System.out.println("None");
        }

        System.out.println("ALLOWED:");
        none = allowedCount == 0;
        for (int i = 0; i < allowedCount; i++) {
            System.out.println(allowedMovieTitles[i] + " (" + allowedMovieLengths[i] + " minutes)");
        }
        if (none) {
            System.out.println("None");
        }
    }
}

