package com.tradingbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kraken")
@Getter
@Setter
public class KrakenProperties
{
    private String apiPublicKey;
    private String apiPrivateKey;

    @Value("${kraken.modify.orders:false}")
    private boolean modifyOrders;
}
