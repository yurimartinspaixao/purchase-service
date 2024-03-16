package com.wex.purchase.application.inputtypes;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Validated
public class PurchaseInput {

    @NotBlank(message = "The description should not be empty")
    @NotNull(message = "The description should not be null")
    @Size(max = 50, message = "The description should not have more than 50 characters")
    private String description;

    @NotNull(message = "The amount should not be null")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "The amount should not have more than 2 fraction digits")
    private BigDecimal amount;

    @NotNull(message = "The transactionDate is not valid")
    private LocalDate transactionDate;

}