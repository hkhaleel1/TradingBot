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
    ;
    private final String value;
}
