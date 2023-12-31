package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Item;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

public class PointsRule5 implements PointsRule {
    @Override
    public boolean evaluate(@Nonnull Receipt receipt) {
        return !receipt.items().isEmpty();
    }

    @Override
    public int getPoints(@Nonnull Receipt receipt) {
        int points = 0;
        for (Item item : receipt.items()) {
            if (item.shortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(item.price() * 0.2);
            }
        }
        return points;
    }
}
