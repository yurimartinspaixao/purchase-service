package com.wex.purchase.datasource.persistence;

import com.wex.purchase.core.model.Purchase;
import com.wex.purchase.core.ports.out.PurchaseOutPutPort;
import com.wex.purchase.datasource.entity.PurchaseEntity;
import com.wex.purchase.datasource.mapper.PurchaseMapper;
import com.wex.purchase.datasource.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchasePersistence implements PurchaseOutPutPort {

    private final PurchaseRepository repository;

    private final PurchaseMapper mapper;

    @Override
    public List<Purchase> findAll() {
        var result = repository.findAll();
        if(!result.isEmpty()) {
            return result.stream().map(mapper::toModel).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Purchase insert(Purchase purchase) {
        PurchaseEntity purchaseEntity = mapper.toEntity(purchase);
        repository.saveAndFlush(purchaseEntity);
        return mapper.toModel(purchaseEntity);
    }
}
