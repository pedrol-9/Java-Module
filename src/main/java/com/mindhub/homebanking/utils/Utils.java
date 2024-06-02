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
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int block = random.nextInt(10000);
            cardNumber.append(String.format("%04d", block));
            if (i < 3) {
                cardNumber.append("-");
            }
        }
        return cardNumber.toString();
    }

    public static int generateCcv() {
        Random random = new Random();
        return 100 + random.nextInt(900);
    }

}