package com.example.fetchrewardsreceiptprocessor.databases;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReceiptDb {

    /**
     * Using ConcurrentHashMap to support multiple requests simultaneously without inconsistencies
     */
    private final Map<String, Integer> db = new ConcurrentHashMap<>();

    public void saveReceiptPoints(@NonNull String receiptId, int points) {
        db.put(receiptId, points);
    }

    @Nullable
    public Integer getReceiptPoints(@NonNull String receiptId) {
        return db.get(receiptId);
    }
}
