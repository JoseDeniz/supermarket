# Supermarket

This project is a simple supermarket where you have a certain number of customers during the day and a certain number of cashiers. Where the final goal is to have the accountability of the cashiers at the end of the day. But, there is only 1 accounting system shared between all the cashiers, so we may have some concurrency issues.

When a customer is ready to pay, the customer must be waiting in a queue, and they are entering the in it with a cadence between 0 and 3 sec. That queue is managed by the cashiers, when a cashier is available, the customer is attended and the time is the same to the price of the cart in seconds. Also, when the supermarket closes, the cashiers do not close immediately, they wait for the queue to be empty.

### How to run

To run the project, you need to have java 17 installed and no extra dependencies are required because the project uses the java standard library.

### Lessons learned

We were playing with the different tools that Java provide to us to implement a concurrent system. We learned how to use the `ExecutorService` to manage the threads (Cashier).

We did an implementation of `LinkedBlockingQueue` to manage the queue of customers, and we how to use `ReentrantLock` and `Condition` to manage the queue. Also, we learned how to use the keywords `synchronized` and `volatile`

Finally, to test the project we used the `CountDownLatch` to wait for the cashiers to finish their work and see the total revenue of the day.

Some other tools that we learned, but we didn't use in the project are `Semaphore`, `Atomic classes` and the object methods `wait()`, `notify()` and `notifyAll()`.

### Possible scenarios to play with

- When a cashier stays idle for a certain time, it closes and the remaining customers are attended by the other cashiers.
- A new cashier can be opened if the queue is too long.
- A cashier is with a customer but suddenly something wrong happens and must close, the customer goes to the queue again but is the first one to be attended by the next available cashier.

### Example of output with 10 customers and 3 cashiers

```
XXX Supermarket Opened! XXX
Cashier #1: opened!
Cashier #2: opened!
Cashier #3: opened!
--> Added customer [Customer 1] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #1: attending customer: Customer 1
--> Added customer [Customer 2] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #2: attending customer: Customer 2
--> Added customer [Customer 3] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #3: attending customer: Customer 3
$$$ Cashier #3: customer attended: Customer 3 $$$ 2612.0
$$$ Cashier #1: customer attended: Customer 1 $$$ 3617.0
--> Added customer [Customer 4] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #3: attending customer: Customer 4
$$$ Cashier #3: customer attended: Customer 4 $$$ 368.0
--> Added customer [Customer 5] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #1: attending customer: Customer 5
--> Added customer [Customer 6] to the queue. Total of customers in queue: 1
<-- Removed customer from the queue. Remaining customers in queue: 0
--> Added customer [Customer 7] to the queue. Total of customers in queue: 1
%%% Cashier #3: attending customer: Customer 6
--> Added customer [Customer 8] to the queue. Total of customers in queue: 2
$$$ Cashier #2: customer attended: Customer 2 $$$ 7540.000000000001
<-- Removed customer from the queue. Remaining customers in queue: 1
%%% Cashier #2: attending customer: Customer 7
--> Added customer [Customer 9] to the queue. Total of customers in queue: 2
--> Added customer [Customer 10] to the queue. Total of customers in queue: 3
XXX Supermarket closed! XXX. Remaining of customers inside: 6
$$$ Cashier #3: customer attended: Customer 6 $$$ 3064.0
<-- Removed customer from the queue. Remaining customers in queue: 2
%%% Cashier #3: attending customer: Customer 8
$$$ Cashier #2: customer attended: Customer 7 $$$ 4304.0
<-- Removed customer from the queue. Remaining customers in queue: 1
%%% Cashier #2: attending customer: Customer 9
$$$ Cashier #1: customer attended: Customer 5 $$$ 8878.0
<-- Removed customer from the queue. Remaining customers in queue: 0
%%% Cashier #1: attending customer: Customer 10
$$$ Cashier #3: customer attended: Customer 8 $$$ 6049.0
$$$ Cashier #1: customer attended: Customer 10 $$$ 952.9999999999999
$$$ Cashier #2: customer attended: Customer 9 $$$ 7764.0
Cashier #1: closed!
Cashier #3: closed!
Cashier #2: closed!
Total revenue of the day: 45149.0000000000009
```