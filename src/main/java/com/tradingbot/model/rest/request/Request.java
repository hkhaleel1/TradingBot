package com.tradingbot.model.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Request
{
    protected String nonce;
    public abstract String toApiPostBodyData();
}
