import java.util.Objects;

public class Money {

    private final double value;
    private final Currency currency;

    public Money(double value, Currency currency) {
        this.value = Math.max(0, value);
        this.currency = currency;
    }

    public Money(double value) {
        this(value, Currency.USD);
    }

    public Money addMoney(Money moneyToAdd) {
        double convertedAmount = this.convertAmount(moneyToAdd.currency, this.currency, moneyToAdd.value);
        return new Money(this.value+convertedAmount, this.currency);
    }

    public Money removeMoney(Money moneyToRemove) {
        double convertedAmount = this.convertAmount(moneyToRemove.currency, this.currency, moneyToRemove.value);
        return new Money(this.value-convertedAmount, this.currency);
    }

    private double convertAmount(Currency from, Currency to, double amount) {
        return Currency.convertMoney(from, to, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return String.format("%s%s", this.value, this.currency);
    }

}
