package com.example.fetchrewardsreceiptprocessor.repositories;

import com.example.fetchrewardsreceiptprocessor.databases.ReceiptDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class ReceiptRepositoryTest {

    @Mock
    private ReceiptDb receiptDb;

    @InjectMocks
    private ReceiptRepository receiptRepository;

    @Test
    public void testSaveReceiptPoints() {

        doNothing().when(receiptDb).saveReceiptPoints(anyString(), anyInt());

        // Method under test
        String generatedReceiptId = receiptRepository.saveReceiptPoints(10);

        // Verification
        verify(receiptDb, times(1)).saveReceiptPoints(anyString(), eq(10));
        Assertions.assertNotNull(generatedReceiptId);
    }

    @Test
    public void testGetReceiptPoints() {
        String receiptId = UUID.randomUUID().toString();
        when(receiptDb.getReceiptPoints(eq(receiptId))).thenReturn(20);

        Integer points = receiptRepository.getReceiptPoints(receiptId);

        verify(receiptDb, times(1)).getReceiptPoints(eq(receiptId));
        Assertions.assertEquals(20, points);
    }
}

