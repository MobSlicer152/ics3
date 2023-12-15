public class Assignment2Bonus {
    public static <T extends Number> boolean check42(T[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    System.out.println("Trying " + array[i] + " + " + array[j]);
                    if (array[i].longValue() + array[j].longValue() == 42l) {
                        System.out.println("That works");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Integer[] array1 = {12, 67, 30, 7};
        Integer[] array2 = {36, 20, 8, 2, 21};

        System.out.printf("Array 1 %s two numbers which add up to 42\n", check42(array1) ? "has" : "doesn't have");
        System.out.printf("Array 2 %s two numbers which add up to 42\n", check42(array2) ? "has" : "doesn't have");
    }
}
