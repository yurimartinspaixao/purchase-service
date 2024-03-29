package com.wex.purchase.core.ports.in;

import com.wex.purchase.core.model.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface RatesOfExchangeOutPutPort {

    List<ExchangeRate> findAll(LocalDate purchaseDate);

}
