package Objects.Enums;

public enum TextColors {
    RESET ("\u001B[0m"),
    BLUE ("\u001B[34m"),
    GREEN ("[32m"),
    RED ("\u001B[31m"),
    ;

    public final String value;
    TextColors(String s) {
        this.value = s;
    }
}
