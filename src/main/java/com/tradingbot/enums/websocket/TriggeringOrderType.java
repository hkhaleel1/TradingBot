package com.tradingbot.enums.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TriggeringOrderType
{
    LIMIT("l"),
    MARKET("m")
    ;
    private final String value;

    public static TriggeringOrderType fromValue(final String value)
    {
        for (TriggeringOrderType type : TriggeringOrderType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TriggeringOrderType value: " + value);
    }
}
