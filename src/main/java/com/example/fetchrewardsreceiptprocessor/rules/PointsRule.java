package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

public interface PointsRule {

    boolean evaluate(@Nonnull Receipt receipt);

    int getPoints(@Nonnull Receipt receipt);
}
