import java.util.List;
import java.util.UUID;

public class Order {

    //Not a value objet because have an id
    private final UUID orderId;
    private List<Product> products;
    private Address addressToDeliver;

    public Order(List<Product> products, Address addressToDeliver) {
        this.orderId = UUID.randomUUID();
        this.products = products;
        this.addressToDeliver = addressToDeliver;
    }
}
