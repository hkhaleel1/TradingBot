package com.tradingbot.model.websocket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubscriptionStatusMessage<T>
{
    private int channelID;
    private String channelName;
    private String event;
    private String pair;
    private String status;
    private T subscription;
}
