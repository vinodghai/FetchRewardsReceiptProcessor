package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;

public class PointsRule4 implements PointsRule {
    @Override
    public boolean evaluate(Receipt receipt) {
        return receipt.items().size() >= 2;
    }

    @Override
    public int getPoints(Receipt receipt) {
        return receipt.items().size() / 2 * 5;
    }
}
