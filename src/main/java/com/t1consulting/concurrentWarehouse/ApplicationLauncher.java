package com.t1consulting.concurrentWarehouse;

public class ApplicationLauncher {

    public static void main(String[] args) {
        int customersQuantity = 10;
        int startingCapacity = 1000;

        Initializer initializer = new Initializer();
        initializer.init(startingCapacity, customersQuantity);
    }
}
