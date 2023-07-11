package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import jakarta.annotation.Nonnull;

import java.util.List;

public class PointsRuleEngine {

    private final List<PointsRule> pointsRules;

    public PointsRuleEngine(@Nonnull List<PointsRule> pointsRules) {
        this.pointsRules = pointsRules;
    }

    public int calculatePoints(@Nonnull Receipt receipt) {
        return pointsRules.stream()
                .filter(pointsRule -> pointsRule.evaluate(receipt))
                .mapToInt(pointsRule -> pointsRule.getPoints(receipt))
                .sum();
    }
}