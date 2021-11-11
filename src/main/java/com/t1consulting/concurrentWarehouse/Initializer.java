package com.t1consulting.concurrentWarehouse;

import com.t1consulting.concurrentWarehouse.entities.Customer;
import com.t1consulting.concurrentWarehouse.entities.Item;
import com.t1consulting.concurrentWarehouse.entities.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Initializer {
    private Warehouse warehouse;
    private CyclicBarrier cyclicBarrier;
    private List<Item> products = new ArrayList<>();
    private List<Customer> customersList = new ArrayList<>();

    public void init(int items, int customers) {
        for (int i = 0; i < items; i++) {
            products.add(new Item());
        }
        cyclicBarrier = new CyclicBarrier(customers);
        warehouse = new Warehouse(products, cyclicBarrier);
        for (int i = 0; i < customers; i++) {
            customersList.add(new Customer(warehouse));
        }
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public List<Item> getProducts() {
        return products;
    }

    public List<Customer> getCustomersList() {
        return customersList;
    }
}
