package calculator;

public enum MainMenuOptions {
    SINGLE_OPERATION("Single operation"),
    TRIG_FUNCTION("Trigonometric function"),
    EXPRESSION("Mathematical expression"),
    REVERSE_POLISH("Reverse Polish notation"),
    TOGGLE_DEGREES("Set degrees or radians");

    private String text;
    private MainMenuOptions(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
};
