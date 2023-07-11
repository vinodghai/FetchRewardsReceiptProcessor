package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;

public class PointsRule3 implements PointsRule {

    @Override
    public boolean evaluate(Receipt receipt) {
        return receipt.total() % 0.25 == 0;
    }

    @Override
    public int getPoints(Receipt receipt) {
        return 25;
    }
}
