public class Address {

    private final Name cityName;
    private final Name streetAddress;

    public Address(Name cityName, Name streetAddress) {
        this.cityName = cityName;
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.cityName.toString(), this.streetAddress.toString());
    }
}
