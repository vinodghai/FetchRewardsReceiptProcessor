package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

public class PointsRule3 implements PointsRule {

    @Override
    public boolean evaluate(@Nonnull Receipt receipt) {
        return receipt.total() % 0.25 == 0;
    }

    @Override
    public int getPoints(@Nonnull Receipt receipt) {
        return 25;
    }
}
