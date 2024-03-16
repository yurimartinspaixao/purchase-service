package com.wex.purchase.application.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.wex.purchase"})
public class GraphQLScalarTypeConfig {

    @Bean
    public GraphQLScalarType json() {
        return ExtendedScalars.Json;
    }
}
