package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

public class PointsRule6 implements PointsRule {
    @Override
    public boolean evaluate(@Nonnull Receipt receipt) {
        return receipt.purchaseDate().getDayOfMonth() % 2 != 0;
    }

    @Override
    public int getPoints(@Nonnull Receipt receipt) {
        return 6;
    }
}
