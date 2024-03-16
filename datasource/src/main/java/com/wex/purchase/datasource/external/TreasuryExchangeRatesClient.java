package com.wex.purchase.datasource.external;

import com.wex.purchase.core.model.ExchangeRateResponse;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "treasuryExchangeRates", url = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1")
public interface TreasuryExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET)
    @RequestLine("/accounting/od/rates_of_exchange?filter={filter}&page[number]={pageNumber}&page[size]={pageSize}&sort={sort}")
    ExchangeRateResponse getExchangeRates(
            @PathVariable(name = "filter") String filter,
            @PathVariable(name = "pageNumber") int pageNumber,
            @PathVariable(name = "pageSize") int pageSize,
            @PathVariable(name = "sort") String sort);
}

