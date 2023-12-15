public class Assignment1 {
    static void sumArrays(int[] a, int[] b, int[] c) {
        for ( int i = 0; i < c.length; i++ ) {
            c[i] = a[i] + b[b.length - i - 1];
        }
    }

    public static void main(String[] args) {
        int a[] = {9, 2, 1, 4};
        int b[] = {3, 5, 1, 0};
        int c[] = new int[4];

        sumArrays(a, b, c);

        System.out.printf("int c[4] = {%d, %d, %d, %d};\n", c[0], c[1], c[2], c[3]);
    }
}
