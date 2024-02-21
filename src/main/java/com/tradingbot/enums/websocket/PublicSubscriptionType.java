package com.tradingbot.enums.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PublicSubscriptionType
{
    TICKER("ticker",0),
    OHLC_1("ohlc", 1),
    OHLC_5("ohlc", 5),
    OHLC_15("ohlc", 15),
    OHLC_30("ohlc", 30),
    OHLC_60("ohlc", 60),
    OHLC_240("ohlc", 240),
    OHLC_1440("ohlc", 1440),
    TRADE("trade", 0),
    SPREAD("spread", 0),
    BOOK("book", 0)
    ;
    private final String value;
    private int interval;

    public static PublicSubscriptionType fromChannelName(final String channelName)
    {
        String[] valueInterval = channelName.split("-");
        String value = valueInterval[0];
        int interval = valueInterval.length > 1 ? Integer.parseInt(valueInterval[1]) : 0;
        for (PublicSubscriptionType type : PublicSubscriptionType.values()) {
            if (type.getValue().equals(value) && type.getInterval() == interval ) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PublicSubscriptionType value: " + channelName);
    }
}
