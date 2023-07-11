package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

public class PointsRule4 implements PointsRule {
    @Override
    public boolean evaluate(@Nonnull Receipt receipt) {
        return receipt.items().size() >= 2;
    }

    @Override
    public int getPoints(@Nonnull Receipt receipt) {
        return receipt.items().size() / 2 * 5;
    }
}
