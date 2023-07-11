package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;

public class PointsRule6 implements PointsRule {
    @Override
    public boolean evaluate(Receipt receipt) {
        return receipt.purchaseDate().getDayOfMonth() % 2 != 0;
    }

    @Override
    public int getPoints(Receipt receipt) {
        return 6;
    }
}
