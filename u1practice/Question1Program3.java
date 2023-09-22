import java.lang.System;

import java.util.HashMap;
import java.util.Scanner;

public class Question1Program3 {
    private static HashMap<String, Double> weightMultipliers;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // This makes it more convenient to print the planet names
        weightMultipliers = new HashMap<String, Double>();
        weightMultipliers.put("Earth", 1.0);
        weightMultipliers.put("Mercury", 0.4);
        weightMultipliers.put("Venus", 0.9);
        weightMultipliers.put("Mars", 0.38);
        weightMultipliers.put("Jupiter", 2.3);
        weightMultipliers.put("Saturn", 1.1);
        weightMultipliers.put("Uranus", 0.92);
        weightMultipliers.put("Neptune", 1.1);

        System.out.print("Enter a weight in pounds (and optionally the name of a planet after): ");
        double weight = scanner.nextDouble();
        String planetName = scanner.next();
        scanner.nextLine();

        Double planetMultiplier = weightMultipliers.get(planetName);
        if (planetMultiplier == null) {
            planetMultiplier = 1.0;
        }
        
        double earthWeight = weight / planetMultiplier;

        weightMultipliers.forEach((String planet, Double multiplier) -> {
            double planetWeight = earthWeight * multiplier;
            System.out.printf("Weight is %.2f lb(s) on %s\n", planetWeight, planet);
        });
    }
}

