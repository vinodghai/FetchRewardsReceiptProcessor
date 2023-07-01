package com.example.fetchrewardsreceiptprocessor.serviecs;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import org.springframework.lang.NonNull;


public interface IReceiptService {

    int calculateReceiptPoints(@NonNull Receipt receipt);

    @NonNull
    String saveReceiptPoints(int points);

    int getReceiptPoints(String receiptId) throws PointsNotFoundException;
}
