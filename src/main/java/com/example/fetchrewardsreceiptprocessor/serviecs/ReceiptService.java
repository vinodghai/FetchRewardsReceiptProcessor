package com.example.fetchrewardsreceiptprocessor.serviecs;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Item;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.repositories.IReceiptRepository;

import java.time.LocalTime;

public class ReceiptService implements IReceiptService {

    private final IReceiptRepository iReceiptRepository;

    public ReceiptService(IReceiptRepository iReceiptRepository) {
        this.iReceiptRepository = iReceiptRepository;
    }

    @Override
    public int calculateReceiptPoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name.
        points += receipt.retailer().replaceAll("\\W", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (receipt.total() % 1 == 0) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (receipt.total() % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        points += receipt.items().size() / 2 * 5;

        // Rule 5: If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer
        for (Item item : receipt.items()) {
            if (item.shortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(item.price() * 0.2);
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        if (receipt.purchaseDate().getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (receipt.purchaseTime().isAfter(LocalTime.parse("14:00")) && receipt.purchaseTime().isBefore(LocalTime.parse("16:00"))) {
            points += 10;
        }

        return points;
    }

    @Override
    public String saveReceiptPoints(int points) {
        return iReceiptRepository.saveReceiptPoints(points);
    }

    @Override
    public int getReceiptPoints(String receiptId) throws PointsNotFoundException {
        Integer points = iReceiptRepository.getReceiptPoints(receiptId);
        if (points == null)
            throw new PointsNotFoundException(receiptId);
        return points;
    }
}
