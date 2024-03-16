package com.wex.purchase.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRate {

    @JsonProperty("recording_date")
    private LocalDate recordDate;
    private String country;
    private String currency;

    @JsonProperty("country_currency_desc")
    private String countryCurrencyDescription;

    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;

    @JsonProperty("effective_date")
    private LocalDate effectiveDate;

    @JsonProperty("src_line_nbr")
    private Integer srcLineNbr;

    @JsonProperty("record_fiscal_year")
    private Integer recordFiscalYear;

    @JsonProperty("record_fiscal_quarter")
    private Integer recordFiscalQuarter;

    @JsonProperty("record_calendar_year")
    private Integer recordCalendarYear;

    @JsonProperty("record_calendar_quarter")
    private Integer recordCalendarQuarter;

    @JsonProperty("record_calendar_month")
    private Integer recordCalendarMonth;

    @JsonProperty("record_calendar_day")
    private Integer recordCalendarDay;

}
