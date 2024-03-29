package concurrency.supermarket.domain;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Supermarket {
    private final SupermarketQueue<Customer> queue;
    private final Accounting accounting;
    private final int numberOfCashiers;
    private final int numberOfCustomers;
    private final CountDownLatch countDownLatch;
    private ExecutorService executorService;

    public Supermarket(int numberOfCustomers, int numberOfCashiers) {
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfCashiers = numberOfCashiers;
        queue = new SupermarketQueue<>();
        accounting = new Accounting();
        countDownLatch = new CountDownLatch(this.numberOfCustomers);
    }

    public void open() throws InterruptedException {
        System.out.println("XXX Supermarket Opened! XXX");
        executorService = Executors.newFixedThreadPool(numberOfCashiers);
        for (int i = 0; i < numberOfCashiers; i++) {
            Cashier cashier = new Cashier(i + 1, queue, accounting, countDownLatch);
            executorService.execute(cashier);
        }

        // Insert customers in the queue with a cadence between 0 and 3 sec
        Random random = new Random();
        var customerCounter = 1;
        while (customerCounter <= numberOfCustomers) {
            var millis = random.nextInt(4) * 1000;
            sleep(millis);
            queue.put(new Customer(customerCounter, new ShoppingCart(random.nextInt(10000) / 100.0)));
            customerCounter += 1;
        }
    }

    public void close() {
        executorService.shutdown();
        System.out.println("XXX Supermarket closed! XXX. Remaining of customers inside: " + countDownLatch.getCount());
        try {
            synchronized (countDownLatch) {
                countDownLatch.await(60, TimeUnit.SECONDS);
                if (!executorService.isTerminated()) {
                    executorService.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Total revenue of the day: " + accounting.getRevenue());
    }
}
