import java.util.List;
import java.util.UUID;

public class Order {

    //Not a value objet because have an id
    private final UUID orderId;
    private Products products;
    private Address addressToDeliver;

    public Order(Products products, Address addressToDeliver) {
        this.orderId = UUID.randomUUID();
        this.products = products;
        this.addressToDeliver = addressToDeliver;
    }
}
