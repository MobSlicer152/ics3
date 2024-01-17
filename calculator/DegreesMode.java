package calculator;

public enum DegreesMode {
    DEGREES("Degrees"),
    RADIANS("Radians");

    private String text;
    private DegreesMode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
