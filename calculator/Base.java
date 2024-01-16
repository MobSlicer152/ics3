package calculator;

public enum Base {
    BINARY("Binary (base 2)", 2),
    OCTAL("Octal (base 8)", 8),
    DECIMAL("Decimal (base 10, default)", 10),
    HEXADECIMAL("Hexadecimal (base 16)", 16);

    String text;
    int radix;

    private Base(String text, int radix) {
        this.text = text;
        this.radix = radix;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getRadix() {
        return radix;
    }
}