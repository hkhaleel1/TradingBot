package com.tradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class TradingBotApplication
{
    public static void main(String[] args) {
        SpringApplication.run(TradingBotApplication.class, args);
    }
}
