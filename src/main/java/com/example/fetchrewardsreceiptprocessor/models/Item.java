package com.example.fetchrewardsreceiptprocessor.models;

import jakarta.validation.constraints.NotNull;

public record Item(@NotNull(message = "shortDescription is required") String shortDescription,
                   @NotNull(message = "price is required") Double price) {
}