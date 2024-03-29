package concurrency.supermarket.domain;

import java.util.concurrent.CountDownLatch;

public class Cashier implements Runnable {
    private final int id;
    private final SupermarketQueue<Customer> queue;
    private final Accounting accounting;
    private final CountDownLatch countDownLatch;

    public Cashier(int id, SupermarketQueue<Customer> queue, Accounting accounting, CountDownLatch countDownLatch) {
        this.id = id;
        this.queue = queue;
        this.accounting = accounting;
        this.countDownLatch = countDownLatch;
        System.out.println(this + " opened!");
    }

    @Override
    public void run() {
        try {
            Customer customer;
            while ((customer = queue.nextCustomer()) != null) {
                var totalPrice = customer.getShoppingCart().getTotalPrice() * 100;
                final var time = (long) totalPrice;
                System.out.println("%%% " + this + " attending customer: " + customer);
                //noinspection BusyWait
                Thread.sleep(time);
                accounting.add(totalPrice);
                countDownLatch.countDown();
                System.out.println("$$$ " + this + " customer attended: " + customer + " $$$ " + totalPrice);
            }
        } catch (InterruptedException e) {
            this.close();
        }
    }

    private void close() {
        System.out.println(this + " closed!");
    }

    @Override
    public String toString() {
        return "Cashier #" + id + ":";
    }
}