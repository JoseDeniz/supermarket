package concurrency.supermarket.domain;

public class Customer {
    private final String name;
    private final ShoppingCart shoppingCart;

    public Customer(int id, ShoppingCart shoppingCart) {
        this.name = "Customer " + id;
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public String toString() {
        return name;
    }
}
