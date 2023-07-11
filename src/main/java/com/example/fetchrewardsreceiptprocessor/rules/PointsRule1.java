package com.example.fetchrewardsreceiptprocessor.rules;

import com.example.fetchrewardsreceiptprocessor.models.Receipt;

public class PointsRule1 implements PointsRule {

    @Override
    public boolean evaluate(Receipt receipt) {
        return !receipt.retailer().isBlank();
    }

    @Override
    public int getPoints(Receipt receipt) {
        return (int) receipt.retailer().chars().filter(this::isAlphanumeric).count();
    }

    /**
     * Checks if a given character code is in the range of alphanumeric characters
     *
     * @param c is input character code
     * @return boolean indicating if given character is an alphanumeric character
     */
    private boolean isAlphanumeric(int c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}
