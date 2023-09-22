import java.lang.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Question1Program2 {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter name %d: ", i + 1);
            names.add(scanner.nextLine());
        }

        Collections.reverse(names);
        boolean first = true;
        for (String name : names) {
            if (first) {
                first = false;
            } else {
                name = ", " + name;
            }
            System.out.print(name);
        }
        System.out.print("\n");
    }
}

