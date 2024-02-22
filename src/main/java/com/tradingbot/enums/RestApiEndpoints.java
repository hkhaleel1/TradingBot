package com.tradingbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RestApiEndpoints
{
    BASE_DOMAIN("https://api.kraken.com"),
    // PUBLIC REST API
    PUBLIC_ENDPOINT("/0/public/"),
    SYSTEM_STATUS ("SystemStatus"),
    ASSET_PAIRS("AssetPairs"),
    TICKER("Ticker"),
    TRADES("Trades"),
    OHLC("OHLC"),

    // PRIVATE REST API
    PRIVATE_ENDPOINT("/0/private/"),
    BALANCE("Balance"),
    // WEBSOCKET
    PUBLIC_WEBSOCKET_URL("wss://ws.kraken.com/"),
    PRIVATE_WEBSOCKET_URL("wss://ws-auth.kraken.com/"),
    GET_WEBSOCKET_TOKEN("GetWebSocketsToken")
    ;
    @Getter
    private final String value;
}
