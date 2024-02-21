package com.tradingbot.enums.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequestEventType
{
    PING("ping"),
//    HEARTBEAT("heartbeat"),
//    SYSTEM_STATUS("systemStatus"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
//    SUBSCRIPTION_STATUS("subscriptionStatus")
    ADD_ORDER("addOrder"),
    EDIT_ORDER("editOrder"),
    CANCEL_ORDER("cancelOrder"),
    CANCEL_ALL("cancelAll"),
    CANCEL_ALL_ORDERS_AFTER("cancelAllOrdersAfter")
    ;
    private final String value;
}
