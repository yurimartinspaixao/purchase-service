package com.wex.purchase.datasource.persistence;

import com.wex.purchase.core.model.Purchase;
import com.wex.purchase.core.ports.out.PurchaseOutPutPort;
import com.wex.purchase.datasource.entity.PurchaseEntity;
import com.wex.purchase.datasource.mapper.PurchaseMapper;
import com.wex.purchase.datasource.repository.PurchaseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchasePersistence implements PurchaseOutPutPort {

    private final PurchaseRepository repository;

    private final PurchaseMapper mapper;

    @Override
    public Purchase findById(Long id) {
        var result = repository.findById(id);
        if(result.isPresent()) {
            return result.map(mapper::toModel).get();
        }
        throw new EntityNotFoundException("Purchase not found");
    }

    @Override
    public Purchase insert(Purchase purchase) {
        PurchaseEntity purchaseEntity = mapper.toEntity(purchase);
        repository.saveAndFlush(purchaseEntity);
        return mapper.toModel(purchaseEntity);
    }
}
