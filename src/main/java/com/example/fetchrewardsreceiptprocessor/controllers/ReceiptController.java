package com.example.fetchrewardsreceiptprocessor.controllers;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.models.ReceiptId;
import com.example.fetchrewardsreceiptprocessor.models.ReceiptPoints;
import com.example.fetchrewardsreceiptprocessor.serviecs.IReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The controller class responsible for handling requests which have prefixes starting with /receipts
 */
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final IReceiptService iReceiptService;

    @Autowired
    public ReceiptController(IReceiptService iReceiptService) {
        this.iReceiptService = iReceiptService;
    }

    @PostMapping("/process")
    public ReceiptId processReceipts(@Valid @RequestBody Receipt receipt) {
        int points = iReceiptService.calculateReceiptPoints(receipt);
        String receiptId = iReceiptService.saveReceiptPoints(points);
        return new ReceiptId(receiptId);
    }

    @GetMapping("/{id}/points")
    public ReceiptPoints getReceiptPoints(@PathVariable("id") String receiptId) throws PointsNotFoundException {
        int points = iReceiptService.getReceiptPoints(receiptId);
        return new ReceiptPoints(points);
    }
}
