package com.example.fetchrewardsreceiptprocessor.serviecs;

import com.example.fetchrewardsreceiptprocessor.ExpectedReceiptPoints;
import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Item;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.repositories.IReceiptRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class ReceiptServiceTest {

    @Mock
    private IReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    private static Stream<ExpectedReceiptPoints> receiptsProvider() {
        ExpectedReceiptPoints one = new ExpectedReceiptPoints(new Receipt("Target",
                LocalDate.parse("2022-01-01"),
                LocalTime.parse("13:01"),
                35.33,
                Arrays.asList(
                        new Item("Mountain Dew 12PK", 6.49),
                        new Item("Emils Cheese Pizza", 12.25),
                        new Item("Knorr Creamy Chicken", 1.26),
                        new Item("Doritos Nacho Cheese", 3.35),
                        new Item("Klarbrunn 12-PK 12 FL OZ", 12.00)
                )), 28);
        ExpectedReceiptPoints two = new ExpectedReceiptPoints(new Receipt("M&M Corner Market",
                LocalDate.parse("2022-03-20"),
                LocalTime.parse("14:33"),
                9.0,
                Arrays.asList(
                        new Item("Gatorade", 2.25),
                        new Item("Gatorade", 2.25),
                        new Item("Gatorade", 2.25),
                        new Item("Gatorade", 2.25)
                )), 109);
        return Stream.of(one, two);
    }


    @ParameterizedTest
    @MethodSource("receiptsProvider")
    public void testCalculateReceiptPoints(ExpectedReceiptPoints expectedReceiptPoints) {
        int points = receiptService.calculateReceiptPoints(expectedReceiptPoints.receipt());
        Assertions.assertEquals(expectedReceiptPoints.points(), points);
    }

    @Test
    public void testSaveReceiptPoints() {
        int points = 50;
        String receiptId = "receipt123";
        when(receiptRepository.saveReceiptPoints(points)).thenReturn(receiptId);
        String generatedReceiptId = receiptService.saveReceiptPoints(points);
        verify(receiptRepository, times(1)).saveReceiptPoints(points);
        Assertions.assertEquals(receiptId, generatedReceiptId);
    }

    @Test
    public void testGetReceiptPoints() throws PointsNotFoundException {
        String receiptId = "receipt123";
        int points = 100;
        when(receiptRepository.getReceiptPoints(receiptId)).thenReturn(points);
        int retrievedPoints = receiptService.getReceiptPoints(receiptId);
        verify(receiptRepository, times(1)).getReceiptPoints(receiptId);
        Assertions.assertEquals(points, retrievedPoints);
    }

    @Test
    public void testGetReceiptPointsNotFound() {
        String receiptId = "receipt123";
        when(receiptRepository.getReceiptPoints(receiptId)).thenReturn(null);
        Assertions.assertThrows(PointsNotFoundException.class, () -> receiptService.getReceiptPoints(receiptId));
        verify(receiptRepository, times(1)).getReceiptPoints(receiptId);
    }
}
