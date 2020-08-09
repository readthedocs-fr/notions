import java.util.Objects;

public class USDRate {

    private final double value;

    public USDRate(double rate) {
        this.value = rate;
    }

    public double convert(USDRate to, double amount) {
        return (this.value / to.value) * amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        USDRate usdRate = (USDRate) o;
        return Double.compare(usdRate.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
