package com.tradingbot.enums.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PrivateSubscriptionType
{
    BALANCES("balances"),
    OWN_TRADES("ownTrades"),
    OPEN_ORDERS("openOrders")
    ;
    private final String value;
}

