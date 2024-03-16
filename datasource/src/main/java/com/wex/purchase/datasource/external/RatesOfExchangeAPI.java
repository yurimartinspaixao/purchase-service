package com.wex.purchase.datasource.external;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.wex.purchase.core.exception.IntegrationException;
import com.wex.purchase.core.exception.model.ErrorMessage;
import com.wex.purchase.core.model.ExchangeRate;
import com.wex.purchase.core.model.ExchangeRateResponse;
import com.wex.purchase.core.ports.in.RatesOfExchangeOutPutPort;
import com.wex.purchase.datasource.deserializers.LocalDateDeserializer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class RatesOfExchangeAPI implements RatesOfExchangeOutPutPort {

    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 1000;
    public static final String SORT_CRITERIA = "currency,-effective_date";
    private final String baseUri;
    private final String apiPath;
    private final WebClient.Builder webClientBuilder;

    public RatesOfExchangeAPI(
            @Value("${fiscal-treasure-data.base-uri}") String baseUri,
            @Value("${fiscal-treasure-data.rates-of-exchange-api}") String apiPath,
            WebClient.Builder webClientBuilder) {
        this.baseUri = baseUri;
        this.apiPath = apiPath;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public List<ExchangeRate> findAll() {
        var response = getResponse();
        var convertedResponse = convertResponse(response);
        return convertedResponse.getData();
    }

    private static ExchangeRateResponse convertResponse(CompletableFuture<String> response) {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
                .fromJson(response.join(), ExchangeRateResponse.class);
    }

    private CompletableFuture<String> getResponse() {
        return webClientBuilder
                .baseUrl(baseUri)
                .build()
                .get()
                .uri(uriBuilder -> getUriWithQueryParams(uriBuilder))
                .exchangeToMono(resp -> {
                    if (resp.statusCode() != HttpStatus.OK
                            && resp.statusCode() != HttpStatus.CREATED
                            && resp.statusCode() != HttpStatus.ACCEPTED) {
                        errorHandling(resp, baseUri);
                    }
                    return resp.bodyToMono(String.class);
                })
                .toFuture();
    }

    private URI getUriWithQueryParams(UriBuilder uriBuilder) {
        LocalDate now = LocalDate.now();
        String filters = String.format("effective_date:gt:%s,effective_date:lte:%s", now.minusMonths(6), now);
        URI build = uriBuilder
                .path(apiPath)
                .queryParam("filter", filters)
                .queryParam("page[number]", PAGE_NUMBER)
                .queryParam("page[size]", PAGE_SIZE)
                .queryParam("sort", SORT_CRITERIA)
                .build();

        log.info("Fetching data from Fiscal Treasure API: " + build);
        return build;
    }

    private void errorHandling(ClientResponse resp, String uri) {
        log.error("Erro while requesting from " + uri);
        log.error("Status code: " + resp.statusCode());

        throw new IntegrationException(ErrorMessage.API_REQUEST_FAIL.getCode(), ErrorMessage.API_REQUEST_FAIL.getMessage());
    }
}
