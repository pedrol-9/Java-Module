package com.mindhub.homebanking.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Utils {
    private static final AtomicLong accountNumberCounter = new AtomicLong(1); // Start from 1000000000
    private static final AtomicLong cardNumberCounter = new AtomicLong(1000000000000000L); // Start from 1000000000000000

    public static String generateAccountNumber() {
        int currentNumber = Math.toIntExact(accountNumberCounter.getAndIncrement());
        return String.format("VIN-%08d", currentNumber);
    }

    public static String generateCardNumber() {
        long number = cardNumberCounter.getAndIncrement();
        String numberStr = String.format("%016d", number);
        return numberStr.replaceAll("(.{4})", "$1-").substring(0, 19);
    }

    public static int generateCcv() {
        Random random = new Random();
        return 100 + random.nextInt(900);
    }
}