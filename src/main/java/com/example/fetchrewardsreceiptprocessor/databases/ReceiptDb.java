package com.example.fetchrewardsreceiptprocessor.databases;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ReceiptDb {

    private final Map<String, Integer> db = new HashMap<>();

    public void saveReceiptPoints(@NonNull String receiptId, int points) {
        db.put(receiptId, points);
    }

    @Nullable
    public Integer getReceiptPoints(@NonNull String receiptId) {
        return db.get(receiptId);
    }
}
