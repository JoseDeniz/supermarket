package concurrency.supermarket;

import concurrency.supermarket.domain.Supermarket;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var supermarket = new Supermarket(10, 3);
        supermarket.open();
        supermarket.close();
    }
}
