package com.tradingbot.model.websocket.response;

import lombok.ToString;

@ToString
public abstract class PublicMessage<T>
{
    protected int channelId;
    protected T data;
    protected String pair;
}
