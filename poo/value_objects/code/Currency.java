public enum Currency {

    EUR(new Name("€"), new USDRate(0.5)),
    USD(new Name("$"), new USDRate(1)),
    TEST(new Name("T"), new USDRate(2));

    private final Name formattedName;
    private final USDRate usdRate;

    Currency(Name formattedName, USDRate usdRate) {
        this.usdRate = usdRate;
        this.formattedName = formattedName;
    }

    public static double convertMoney(Currency from, Currency to, double amount) {
        if(from.equals(to)) {
            return amount;
        }
        return from.usdRate.convert(to.usdRate, amount);
    }

    @Override
    public String toString() {
        return this.formattedName.toString();
    }
}
