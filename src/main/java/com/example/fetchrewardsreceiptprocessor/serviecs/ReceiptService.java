package com.example.fetchrewardsreceiptprocessor.serviecs;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.repositories.IReceiptRepository;
import com.example.fetchrewardsreceiptprocessor.rules.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service component that will be injected while wriring dependencies by Spring Framework
 * @Service annotations indicates this a service layer which implements the business logic,
 * and this class' instance should be used whenever there is a dependency for IReceiptRepository interface
 */

@Service
public class ReceiptService implements IReceiptService {

    private final IReceiptRepository iReceiptRepository;

    private final PointsRuleEngine pointsRuleEngine;

    @Autowired
    public ReceiptService(IReceiptRepository iReceiptRepository) {
        this.iReceiptRepository = iReceiptRepository;
        List<PointsRule> pointsRules = Arrays.asList(
                new PointsRule1(),
                new PointsRule2(),
                new PointsRule3(),
                new PointsRule4(),
                new PointsRule5(),
                new PointsRule6(),
                new PointsRule7()
        );
        this.pointsRuleEngine = new PointsRuleEngine(pointsRules);
    }

    /**
     * Calculates points of a receipt using predefined rules in rules engine
     *
     * @param receipt object from the request
     * @return points calculated from the contents of receipt
     */
    @Override
    public int calculateReceiptPoints(Receipt receipt) {
        return pointsRuleEngine.calculatePoints(receipt);
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
