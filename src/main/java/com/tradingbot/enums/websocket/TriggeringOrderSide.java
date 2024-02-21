package com.tradingbot.enums.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TriggeringOrderSide
{
    BUY("b"),
    SELL("s")
    ;
    private final String value;

    public static TriggeringOrderSide fromValue(final String value)
    {
        for (TriggeringOrderSide type : TriggeringOrderSide.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TriggeringOrderSide value: " + value);
    }
}
