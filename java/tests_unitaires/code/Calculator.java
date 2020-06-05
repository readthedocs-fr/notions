public class Calculator {

    private final int a;
    private final NumberProvider numberProvider;

    public Calculator(int a, NumberProvider numberProvider) {
        this.a = a;
        this.numberProvider = numberProvider;
    }

    public int add(int b) {
        return a + b;
    }

    public float divide(int b) {

        if (b == 0) {
            throw new IllegalArgumentException("Denominator must be non null.");
        }

        return (float) a / b;
    }

    public int addToNumber() {
        return a + numberProvider.provideNumber();
    }

}