package com.t1consulting.concurrentWarehouse.entities;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Logger;

public class Customer extends Thread {

    final Logger log = Logger.getLogger("Customer");

    private int boughtItems;
    private int completeOrders;
    private final Warehouse warehouse;

    private final int MIN = 1;
    private final int MAX = 10;

    private int getItemsInOrder() {
        return new Random().ints(MIN, MAX).findFirst().getAsInt();
    }

    public Customer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        buy();
    }

    public void finish() {
        log.info("Customer " + Thread.currentThread().getName() + " bought " + boughtItems + " items in " + completeOrders + " orders");
        this.interrupt();
    }

    public void buy() {
        while (!this.isInterrupted()) {
            int boughtProducts = warehouse.sell(getItemsInOrder());
            if (boughtProducts != 0) {
                try {
                    completeOrders++;
                    boughtItems += boughtProducts;
                    warehouse.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    finish();
                }
            } else {
                finish();
            }
        }
    }

    public int getBoughtItems() {
        return boughtItems;
    }

    public int getCompleteOrders() {
        return completeOrders;
    }
}
