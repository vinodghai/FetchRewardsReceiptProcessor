package com.example.fetchrewardsreceiptprocessor.exceptions;

public class PointsNotFoundException extends Exception {

    public PointsNotFoundException(String receiptId) {
        super(String.format("Unable to find points for requestId: %s", receiptId));
    }
}
