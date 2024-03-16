package com.wex.purchase.application.mapper;

import com.wex.purchase.application.inputtypes.PurchaseInput;
import com.wex.purchase.core.model.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseInputMapper {

    Purchase toModel(PurchaseInput purchase);
    PurchaseInput toEntity(Purchase purchase);
}
