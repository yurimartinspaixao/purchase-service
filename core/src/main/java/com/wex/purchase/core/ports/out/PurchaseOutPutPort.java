package com.wex.purchase.core.ports.out;

import com.wex.purchase.core.model.Purchase;

public interface PurchaseOutPutPort {

    Purchase findById(Long id);

    Purchase insert(Purchase purchase);
}
