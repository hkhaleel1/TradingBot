package com.tradingbot.model.websocket.request;

import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.websocket.PublicSubscriptionType;
import com.tradingbot.enums.websocket.RequestEventType;
import com.tradingbot.model.websocket.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TradeEvent
{
    private String event;
    private Subscription subscription;
    private String[] pair;
    public TradeEvent (final RequestEventType eventType,
                       final PublicSubscriptionType subscriptionType,
                       final CurrencyPair currencyPair)
    {
        this.event = eventType.getValue();
        this.subscription = subscriptionType.getInterval() > 0 ?
                new OHLCSubscription(subscriptionType.getValue(), subscriptionType.getInterval())
                : new Subscription(subscriptionType.getValue());
        this.pair = new String[]{currencyPair.toString()};
    }
}
