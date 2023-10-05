import java.lang.Math;
import java.lang.System;
import java.util.Random;
import java.util.Scanner;

public class Main {
  // These are lists of words that make the name of the paint store "creative"
  private static final String[] ADJECTIVES = {
      "Affordable", "Appealing", "Convenient", "Friendly", "Modern",
      "Organized",  "Reliable",  "Unique",     "The"};
  private static final String[] STORE_SYNONYMS = {
      "Store", "Shop", "Bazaar", "Market", "Emporium", "Mart", "Exchange"};

  // Conversion rates
  private static final double PAINT_CANS_PER_SQUARE_METRE = 4;
  private static final double PAINT_CAN_PRICE = 24.50;
  private static final double TAX = 13.0 / 100.0; // could also write 0.13

  // https://en.wikipedia.org/wiki/ANSI_escape_code
  private static final String RED = "\033[1;31m";
  private static final String BLUE = "\033[1;34m";
  private static final String RESET = "\033[0m";

  public static void main(String[] args) {
    // Create instances of utility classes
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    // Modulo also makes numbers wrap around so the number will be within the
    // bounds of the arrays
    String adjective =
        ADJECTIVES[Math.abs(random.nextInt()) % ADJECTIVES.length];
    String store =
        STORE_SYNONYMS[Math.abs(random.nextInt()) % STORE_SYNONYMS.length];

    // Welcome the user
    System.out.printf(
        "Welcome to %s Paint %s\n\nYou've got walls!\nWe've got paint!\n\n",
        adjective, store);

    // Get information
    System.out.print("Enter the width of the wall in metres: ");
    System.out.print(RED);
    double width = scanner.nextDouble();
    System.out.print(RESET);
    System.out.print("Enter the height of the wall in metres: ");
    System.out.print(RED);
    double height = scanner.nextDouble();
    System.out.print(RESET);

    // Calculations
    double area = width * height;
    int paintCans = (int)Math.ceil(area / PAINT_CANS_PER_SQUARE_METRE);
    double price = paintCans * PAINT_CAN_PRICE;
    double tax = price * TAX;
    double total = price + tax;

    System.out.printf("\nThe surface you wish to paint is %s%.2f%s m^2\n", BLUE,
                      area, RESET);
    System.out.printf("That means you need %s%d%s cans of paint\n", BLUE,
                      paintCans, RESET);

    System.out.printf(
        "\nSub-total: $%s%.2f%s\nTax: $%s%.2f%s\nTotal: $%s%.2f%s\n", BLUE,
        price, RESET, BLUE, tax, RESET, BLUE, total, RESET);
  }
}
