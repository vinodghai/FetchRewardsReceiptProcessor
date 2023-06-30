package com.example.fetchrewardsreceiptprocessor.repositories;

import com.example.fetchrewardsreceiptprocessor.databases.ReceiptDb;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class ReceiptRepository implements IReceiptRepository {

    private final ReceiptDb receiptDb;

    public ReceiptRepository(ReceiptDb receiptDb) {
        this.receiptDb = receiptDb;
    }

    @Override
    public String saveReceiptPoints(int points) {
        String receiptId = generateReceiptId();
        receiptDb.saveReceiptPoints(receiptId, points);
        return receiptId;
    }

    @Nullable
    @Override
    public Integer getReceiptPoints(String receiptId) {
        return receiptDb.getReceiptPoints(receiptId);
    }

    private String generateReceiptId() {
        return UUID.randomUUID().toString();
    }
}
