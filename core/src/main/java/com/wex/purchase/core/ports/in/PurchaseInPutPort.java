package com.wex.purchase.core.ports.in;

import com.wex.purchase.core.model.Purchase;

public interface PurchaseInPutPort {

    Purchase findPurchase(Long id);

    Purchase insert(Purchase purchase);


}
