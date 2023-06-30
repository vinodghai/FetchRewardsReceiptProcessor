package com.example.fetchrewardsreceiptprocessor.repositories;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IReceiptRepository {

    String saveReceiptPoints(int points);

    @Nullable
    Integer getReceiptPoints(@NonNull String receiptId);
}
