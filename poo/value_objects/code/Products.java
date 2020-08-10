import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Money getProductsCost() {
        Money money = new Money(0);
        for (Product product : products) {
            Money productCost = product.getCost();
            money = money.addMoney(productCost);
        }
        return money;
    }

}
