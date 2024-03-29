package concurrency.supermarket.domain;

import java.math.BigDecimal;

public class Accounting {

    private volatile BigDecimal revenue;

    public Accounting() {
        this.revenue = new BigDecimal(0);
    }

    public synchronized void add(double value) {
        revenue = revenue.add(BigDecimal.valueOf(value));
    }

    public String getRevenue() {
        return revenue.toEngineeringString();
    }
}
