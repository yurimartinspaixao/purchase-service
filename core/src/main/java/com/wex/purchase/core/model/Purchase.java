package com.wex.purchase.core.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private Map<String, BigDecimal> rates;

}
