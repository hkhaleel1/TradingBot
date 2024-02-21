package com.tradingbot.model.websocket.request;

import com.tradingbot.enums.websocket.PrivateSubscriptionType;
import com.tradingbot.enums.websocket.RequestEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrivateEvent
{
    private String event;
    private PrivateSubscription subscription;

    public PrivateEvent (final RequestEventType eventType,
                         final PrivateSubscriptionType subscriptionType,
                         final String token)
    {
        this.event = eventType.getValue();
        this.subscription = new PrivateSubscription(subscriptionType.getValue(), token);
    }
}
