import java.lang.System;

import java.util.Scanner;

public class Program1 {
    private static String getGradeString(int gradePercent) {
        String grade = new String();

        int tens = gradePercent / 10;
        int ones = gradePercent % 10;

        if (tens >= 9) {
            grade += "A";
        } else if (tens == 8) {
            grade += "B";
        } else if (tens == 7) {
            grade += "C";
        } else if (tens == 6) {
            grade += "D";
        } else {
            grade += "F";
        }

        if (tens > 5 && tens < 10) {
            if (ones >= 8) {
                grade += "+";
            } else if (ones <= 2) {
                grade += "-";
            }
        } else if (tens >= 10) {
            grade += "+";
        }

        return grade;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your grade as an integer: ");
        int grade = scanner.nextInt();

        System.out.printf("Your grade is %s\n", getGradeString(grade));
    }
}

