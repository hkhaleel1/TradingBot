package com.tradingbot.model.websocket.request;

import com.tradingbot.model.websocket.Subscription;
import lombok.Getter;

@Getter
public class PrivateSubscription extends Subscription
{
    private final String token;

    public PrivateSubscription(final String name, final String token)
    {
        super(name);
        this.token = token;
    }
}
