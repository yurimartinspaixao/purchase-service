package com.wex.purchase.application.controllers;

import com.wex.purchase.application.inputtypes.PurchaseInput;
import com.wex.purchase.application.mapper.PurchaseInputMapper;
import com.wex.purchase.core.model.Purchase;
import com.wex.purchase.core.ports.in.PurchaseInPutPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    @Autowired
    private final PurchaseInPutPort purchaseInPutPort;

    @Autowired
    private final PurchaseInputMapper mapper;

    @QueryMapping
    public List<Purchase> findAllPurchases() {
        return purchaseInPutPort.findAll();
    }

    @MutationMapping
    public Purchase insertPurchaseTransaction(@Argument @Valid PurchaseInput purchase) {
        return purchaseInPutPort.insert(mapper.toModel(purchase));
    }
}
