import java.util.Objects;

public class Money {

    //value object
    private final long value;
    private final Currency currency;

    public Money(long value, Currency currency) {
        this.value = Math.max(0, value);
        this.currency = currency;
    }

    public Money(long value) {
        this(value, Currency.USD);
    }

    public Money addMoney(Money moneyToAdd) {
        return new Money(this.value+moneyToAdd.value, this.currency);
    }

    public Money removeMoney(Money moneyToRemove) {
        return new Money(this.value-moneyToRemove.value, this.currency);
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
