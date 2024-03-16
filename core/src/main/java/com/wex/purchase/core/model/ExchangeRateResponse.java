package com.wex.purchase.core.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateResponse {

    private List<ExchangeRate> data;
    private Map<String, String> metadata;
    private Map<String, String> links;

}
