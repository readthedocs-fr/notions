public class Address {

    //value object
    private final String cityName;
    private final String streetAddress;

    public Address(String cityName, String streetAddress) {
        this.cityName = cityName;
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.cityName, this.streetAddress);
    }
}
