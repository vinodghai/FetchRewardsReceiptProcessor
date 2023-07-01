package com.example.fetchrewardsreceiptprocessor.serviecs;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Item;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.repositories.IReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * Service component that will be injected while wriring dependencies by Spring Framework
 * @Service annotations indicates this a service layer which implements the business logic,
 * and this class' instance should be used whenever there is a dependency for IReceiptRepository interface
 */

@Service
public class ReceiptService implements IReceiptService {

    private final IReceiptRepository iReceiptRepository;

    @Autowired
    public ReceiptService(IReceiptRepository iReceiptRepository) {
        this.iReceiptRepository = iReceiptRepository;
    }

    /**
     * Calculates points of a receipt using predefined rules
     * @param receipt object from the request
     * @return points calculated from the contents of receipt
     */
    @Override
    public int calculateReceiptPoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name.
        points += receipt.retailer().chars().filter(this::isAlphanumeric).count();

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

    /**
     * Checks if a given character code is in the range of alphanumeric characters
     * @param c is input character code
     * @return boolean indicating if given character is an alphanumeric character
     */
    private boolean isAlphanumeric(int c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
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
