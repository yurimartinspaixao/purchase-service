package com.wex.purchase.datasource.mapper;

import com.wex.purchase.datasource.entity.PurchaseEntity;
import com.wex.purchase.core.model.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    Purchase toModel(PurchaseEntity purchase);
    PurchaseEntity toEntity(Purchase purchase);
}
