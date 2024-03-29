package concurrency.supermarket.domain;

import java.math.BigDecimal;

public class ShoppingCart {
    private final BigDecimal totalPrice;

    public ShoppingCart(double totalPrice) {
        this.totalPrice = BigDecimal.valueOf(totalPrice);
    }

    public double getTotalPrice() {
        return totalPrice.doubleValue();
    }
}

