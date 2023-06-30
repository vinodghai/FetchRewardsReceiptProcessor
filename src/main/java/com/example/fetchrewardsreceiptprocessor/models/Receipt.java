package com.example.fetchrewardsreceiptprocessor.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Receipt(@NotNull(message = "retailer is required") String retailer,
                      @NotNull(message = "purchaseDate is required") LocalDate purchaseDate,
                      @NotNull(message = "purchaseTime is required") LocalTime purchaseTime,
                      @NotNull(message = "total is required") Double total,
                      @Valid
                      @NotNull(message = "Items are required")
                      @Size(min = 1, message = "there must be at least one item in items") List<Item> items) {

    public String toJson() {
        try {
            return JsonMapper.builder().addModule(new JavaTimeModule()).build().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
