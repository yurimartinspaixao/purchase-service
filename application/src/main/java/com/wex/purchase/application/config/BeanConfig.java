package com.wex.purchase.application.config;


import com.wex.purchase.core.ports.in.PurchaseInPutPort;
import com.wex.purchase.core.ports.in.RatesOfExchangeOutPutPort;
import com.wex.purchase.core.ports.out.PurchaseOutPutPort;
import com.wex.purchase.core.usecases.PurchaseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.wex.purchase"})
public class BeanConfig {

    @Bean
    PurchaseInPutPort purchaseInPutPort(PurchaseOutPutPort outPutPort, RatesOfExchangeOutPutPort ratesOfExchangeOutPutPort) {
        return new PurchaseUseCase(outPutPort, ratesOfExchangeOutPutPort);
    }
}
