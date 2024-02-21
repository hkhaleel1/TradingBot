package com.tradingbot.model.websocket.response;

import lombok.ToString;

import java.util.List;

@ToString
public abstract class PublicMessage<T>
{
    protected int channelId;
    protected T data;
    protected String pair;

    protected abstract T mapData(List<Object> data);
}
