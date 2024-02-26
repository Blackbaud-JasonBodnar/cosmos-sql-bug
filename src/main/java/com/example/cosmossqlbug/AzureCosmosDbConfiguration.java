package com.example.cosmossqlbug;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.cosmos.ThrottlingRetryOptions;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.mapping.EnableCosmosAuditing;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCosmosRepositories(basePackages = "com.example.cosmossqlbug")
@EnableCosmosAuditing
public class AzureCosmosDbConfiguration extends AbstractCosmosConfiguration {

    @Value("${azure.cosmosdb.uri}")
    private String uri;

    @Value("${azure.cosmosdb.key}")
    private String key;

    @Value("${azure.cosmosdb.database}")
    private String dbName;

    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        AzureKeyCredential azureKeyCredential = new AzureKeyCredential(key);
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        GatewayConnectionConfig gatewayConnectionConfig = new GatewayConnectionConfig();
        ThrottlingRetryOptions throttlingRetryOptions = new ThrottlingRetryOptions()
                .setMaxRetryAttemptsOnThrottledRequests(0);
        return new CosmosClientBuilder()
                .endpoint(uri)
                .credential(azureKeyCredential)
                .directMode(directConnectionConfig, gatewayConnectionConfig)
                .throttlingRetryOptions(throttlingRetryOptions);
    }

    @Override
    public CosmosConfig cosmosConfig() {
        return CosmosConfig.builder()
                .build();
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}
