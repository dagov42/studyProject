package com.t1consulting.concurrentWarehouse.entities;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class Warehouse {

    final Logger log = Logger.getLogger("Warehouse");

    protected BlockingDeque<Item> itemsQueue;
    public CyclicBarrier barrier;

    public Warehouse(List<Item> items, CyclicBarrier cyclicBarrier) {
        this.itemsQueue = new LinkedBlockingDeque<>(items);
        this.barrier = cyclicBarrier;
    }

    public synchronized int sell(int items) {
        if (!itemsQueue.isEmpty()) {
            if (itemsQueue.size() < items) {
                items = itemsQueue.size();
            }
            for (int i = 0; i < items; i++) {
                itemsQueue.poll();
            }
            log.info(Thread.currentThread().getName() + " bought " + items + " items. LEFT: " + itemsQueue.size());
            return items;
        } else {
            return 0;
        }
    }
}
