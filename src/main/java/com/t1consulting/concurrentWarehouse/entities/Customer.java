package com.t1consulting.concurrentWarehouse.entities;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Customer {

    final Logger log = Logger.getLogger("Customer");

    private int boughtItems;
    private AtomicInteger completeOrders;
    private Thread customer;
    private Warehouse warehouse;

    private final int MIN = 1;
    private final int MAX = 10;

    private int getItemsInOrder() {
        return new Random().ints(MIN, MAX).findFirst().getAsInt();
    }

    public Customer(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.completeOrders = new AtomicInteger(0);
        this.start();
    }

    public void start() {
        customer = new Thread(() -> {
            log.info("Customer " + Thread.currentThread().getName() + " went to the Warehouse");
            while (!Thread.currentThread().isInterrupted()) {
                buy(getItemsInOrder());

            }
        });
        customer.start();
    }

    public void stop() {
        log.info("Customer " + Thread.currentThread().getName() + " bought " + boughtItems + " items in " + completeOrders + " orders");
        if (customer != null) {
            customer.interrupt();
            customer = null;
        }
    }

    public void buy(int items) {
        while (customer != null && !customer.isInterrupted()) {
            int boughtProducts = warehouse.sell(items);
            if (boughtProducts != 0) {
                try {
                    warehouse.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    log.warning("Something went wrong: " + Arrays.toString(e.getStackTrace()));
                }
                completeOrders.incrementAndGet();
                boughtItems += boughtProducts;
            } else {
                warehouse.barrier.reset();
                stop();
            }
        }
    }

    public int getBoughtItems() {
        return boughtItems;
    }

    public AtomicInteger getCompleteOrders() {
        return completeOrders;
    }
}
