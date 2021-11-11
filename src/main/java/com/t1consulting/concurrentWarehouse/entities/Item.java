package com.t1consulting.concurrentWarehouse.entities;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.MessageFormat;

public class Item {
    private final String codePattern = "code_{0}";
    private String code;
    private Integer serialNumber;

    public Item() {
        this.code = MessageFormat.format(codePattern, RandomStringUtils.randomNumeric(5));
        this.serialNumber = RandomUtils.nextInt();
    }
}
