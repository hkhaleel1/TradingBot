package com.tradingbot.model.rest.request;

public class Balance extends Request
{
    public Balance(String nonce)
    {
        super(nonce);
    }

    @Override
    public String toApiPostBodyData()
    {
        return "nonce=" + nonce;
    }
}
