package com.tradingbot.service;

import com.tradingbot.utils.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class KrakenService
{
    @Autowired
    protected JsonMapper mapper;
}
