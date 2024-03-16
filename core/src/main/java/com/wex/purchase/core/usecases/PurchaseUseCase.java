package com.wex.purchase.core.usecases;

import com.wex.purchase.core.model.ExchangeRate;
import com.wex.purchase.core.model.Purchase;
import com.wex.purchase.core.ports.in.PurchaseInPutPort;
import com.wex.purchase.core.ports.in.RatesOfExchangeOutPutPort;
import com.wex.purchase.core.ports.out.PurchaseOutPutPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PurchaseUseCase implements PurchaseInPutPort {

    private final PurchaseOutPutPort persistenceAdapter;

    private final RatesOfExchangeOutPutPort ratesOfExchangeAdapter;

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = persistenceAdapter.findAll();
        List<ExchangeRate> rates = ratesOfExchangeAdapter.findAll();

        purchases.forEach(purchase -> {
            Map<String, ExchangeRate> ratesWithNoDuplicates = rates.stream().collect(
                    Collectors.toMap(ExchangeRate::getCurrency, Function.identity(), compareEffectiveDateToMerge()));

            Map<String, BigDecimal> convertedRates =
                    ratesWithNoDuplicates.entrySet().stream().collect(
                            Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> entry.getValue().getExchangeRate()
                                            .multiply(purchase.getAmount())
                                            .setScale(2, RoundingMode.FLOOR)
                            ));

            purchase.setRates(convertedRates);
        });

        return purchases;
    }

    private static BinaryOperator<ExchangeRate> compareEffectiveDateToMerge() {
        return (existingValue, newValue) ->
                existingValue.getEffectiveDate().isAfter(newValue.getEffectiveDate()) ? existingValue : newValue;
    }

    private BigDecimal calculateRates(ExchangeRate entry, Purchase purchase) {
        BigDecimal exchangeRate = entry.getExchangeRate();
        BigDecimal amount = purchase.getAmount();
        if(Objects.nonNull(exchangeRate) && Objects.nonNull(amount)) {
            return amount.multiply(exchangeRate);
        }
        return null;
    }

    @Override
    public Purchase insert(Purchase purchase) {
        return persistenceAdapter.insert(purchase);
    }

}
