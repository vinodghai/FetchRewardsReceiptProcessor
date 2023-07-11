package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

import java.time.LocalTime;

public class PointsRule7 implements PointsRule {
    @Override
    public boolean evaluate(@Nonnull Receipt receipt) {
        return receipt.purchaseTime().isAfter(LocalTime.parse("14:00")) && receipt.purchaseTime().isBefore(LocalTime.parse("16:00"));
    }

    @Override
    public int getPoints(@Nonnull Receipt receipt) {
        return 10;
    }
}
