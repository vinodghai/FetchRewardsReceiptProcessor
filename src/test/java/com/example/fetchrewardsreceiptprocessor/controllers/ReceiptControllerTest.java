package com.example.fetchrewardsreceiptprocessor.controllers;

import com.example.fetchrewardsreceiptprocessor.exceptions.PointsNotFoundException;
import com.example.fetchrewardsreceiptprocessor.models.Item;
import com.example.fetchrewardsreceiptprocessor.models.Receipt;
import com.example.fetchrewardsreceiptprocessor.serviecs.IReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptController.class)
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReceiptService receiptService;

    @Test
    public void testProcessReceipts() throws Exception {
        Receipt receipt = new Receipt("Target",
                LocalDate.parse("2022-01-01"),
                LocalTime.parse("13:01"),
                35.33,
                Arrays.asList(
                        new Item("Mountain Dew 12PK", 6.49),
                        new Item("Emils Cheese Pizza", 12.25),
                        new Item("Knorr Creamy Chicken", 1.26),
                        new Item("Doritos Nacho Cheese", 3.35),
                        new Item("Klarbrunn 12-PK 12 FL OZ", 12.00)
                ));
        String receiptId = "abcd1234";

        Mockito.when(receiptService.calculateReceiptPoints(receipt)).thenReturn(28);
        Mockito.when(receiptService.saveReceiptPoints(eq(28))).thenReturn(receiptId);

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receipt.toJson()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(receiptId));
    }

    @Test
    public void testProcessReceipts_InvalidRequest() throws Exception {
        Receipt receipt = new Receipt(null,
                LocalDate.parse("2022-01-01"),
                LocalTime.parse("13:01"),
                35.33,
                Arrays.asList(
                        new Item("Mountain Dew 12PK", 6.49),
                        new Item("Emils Cheese Pizza", 12.25),
                        new Item("Knorr Creamy Chicken", 1.26),
                        new Item("Doritos Nacho Cheese", 3.35),
                        new Item("Klarbrunn 12-PK 12 FL OZ", 12.00)
                ));
        String invalidRequestBody = receipt.toJson();

        mockMvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType("application/json")
                        .content(invalidRequestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").exists());
    }

    @Test
    public void testGetReceiptPoints() throws Exception {
        String receiptId = "abcd1234";
        int points = 100;
        Mockito.when(receiptService.getReceiptPoints(eq(receiptId))).thenReturn(points);
        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/{id}/points", receiptId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.points").value(points));
    }

    @Test
    public void testGetReceiptPoints_ThrowsPointsNotFoundException() throws Exception {
        String receiptId = "invalidId";
        Mockito.when(receiptService.getReceiptPoints(eq(receiptId))).thenThrow(PointsNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/receipts/{id}/points", receiptId))
                .andExpect(status().isNotFound());
    }
}
