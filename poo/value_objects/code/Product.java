public class Product {

    private final Money cost;

    public Product(Money cost) {
        this.cost = cost;
    }

    public Money getCost() {
        return cost;
    }
}
