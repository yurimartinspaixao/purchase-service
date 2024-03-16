package com.wex.purchase.core.ports.in;

import com.wex.purchase.core.model.Purchase;

import java.util.List;

public interface PurchaseInPutPort {

    List<Purchase> findAll();

    Purchase insert(Purchase purchase);


}
