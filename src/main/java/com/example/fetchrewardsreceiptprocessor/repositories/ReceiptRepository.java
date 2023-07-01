package com.example.fetchrewardsreceiptprocessor.repositories;

import com.example.fetchrewardsreceiptprocessor.databases.ReceiptDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository component that will be injected while wriring dependencies by Spring Framework
 * @Repository annotations indicates this a repository layer and this class' instance should be used
 * whenever there is a dependency for IReceiptRepository interface
 */
@Repository
public class ReceiptRepository implements IReceiptRepository {

    private final ReceiptDb receiptDb;

    @Autowired
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

    /**
     * Generates a unique Id for the receipt using UUID
     * @return unique UUID string
     */
    private String generateReceiptId() {
        return UUID.randomUUID().toString();
    }
}
