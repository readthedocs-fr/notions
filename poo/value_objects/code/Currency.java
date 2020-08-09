public enum Currency {

    EUR("€"),
    USD("$");

    private final String formattedString;

    Currency(String formattedString) {
        this.formattedString = formattedString;
    }

    @Override
    public String toString() {
        return this.formattedString;
    }
}
