package com.wex.purchase.datasource.repository;

import com.wex.purchase.datasource.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
