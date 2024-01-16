package calculator;

public enum MainMenuOptions {
    SINGLE_OPERATION("Single operation"),
    FUNCTION("Function"),
    EXPRESSION("Mathematical expression"),
    REVERSE_POLISH("Reverse Polish notation"),
    SET_BASE("Set base"),
    QUIT("Quit");

    private String text;
    private MainMenuOptions(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
};
