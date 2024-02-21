package com.tradingbot.model.websocket.request;

import com.tradingbot.model.websocket.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OHLCSubscription extends Subscription
{
    private int interval;
    public OHLCSubscription(final String name, final int interval)
    {
        super(name);
        this.interval = interval;
    }
}
