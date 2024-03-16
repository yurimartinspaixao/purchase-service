package com.wex.purchase.datasource.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "purchase")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="description")
    private String description;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="transaction_date")
    private LocalDate transactionDate;

}
