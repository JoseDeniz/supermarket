package concurrency.supermarket.domain;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Queue with the same behaviour as LinkedBlockingQueue
 */
public class SupermarketQueue<C extends Customer> {
    private final Queue<C> customers;
    private final ReentrantLock lock;
    private final Condition notEmpty;

    public SupermarketQueue() {
        customers = new LinkedList<>();
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
    }

    public void put(C customer) {
        this.lock.lock();
        try {
            this.customers.add(customer);
            this.notEmpty.signal();
            System.out.println("--> Added customer [" + customer + "] to the queue. Total of customers in queue: " + customers.size());
        } finally {
            this.lock.unlock();
        }
    }

    public C nextCustomer() throws InterruptedException {
        this.lock.lock();
        try {
            while (this.customers.isEmpty()) {
                this.notEmpty.await();
            }
            var customer = this.customers.remove();
            System.out.println("<-- Removed customer from the queue. Remaining customers in queue: " + customers.size());
            return customer;
        } finally {
            this.lock.unlock();
        }
    }
}
