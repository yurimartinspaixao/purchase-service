package com.wex.purchase.datasource.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EntityScan(basePackages = {"com.wex.purchase.datasource.entity"})
@EnableJpaRepositories(basePackages = {"com.wex.purchase.datasource.repository"})
public class HikariConfiguration {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private Integer connectionTimeout;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.hikari.pool-name}")
    private String poolName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setDriverClassName(driver);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaximumPoolSize(maxPoolSize);
        config.setPoolName(poolName);

        return new HikariDataSource(config);
    }
}
