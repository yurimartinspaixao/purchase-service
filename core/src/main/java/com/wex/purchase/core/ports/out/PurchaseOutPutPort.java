package com.wex.purchase.core.ports.out;

import com.wex.purchase.core.model.Purchase;

import java.util.List;

public interface PurchaseOutPutPort {

    List<Purchase> findAll();

    Purchase insert(Purchase purchase);
}
