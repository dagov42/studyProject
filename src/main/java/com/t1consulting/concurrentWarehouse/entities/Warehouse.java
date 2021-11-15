package com.t1consulting.concurrentWarehouse.entities;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Warehouse {

    final Logger log = Logger.getLogger("Warehouse");

    protected ConcurrentLinkedQueue<Item> itemsQueue;
    public CyclicBarrier barrier;

    public Warehouse(List<Item> items, CyclicBarrier cyclicBarrier) {
        this.itemsQueue = new ConcurrentLinkedQueue<>(items);
        this.barrier = cyclicBarrier;
    }

    public int sell(int items) {
        if (!itemsQueue.isEmpty()) {
            for (int i = 0; i < items; i++) {
                synchronized (this) {
                    if (itemsQueue.iterator().hasNext()) {
                        itemsQueue.poll();
                    } else {
                        barrier.reset();
                        break;
                    }
                }
            }
            return items;
        } else {
            barrier.reset();
            return 0;
        }
    }
}
