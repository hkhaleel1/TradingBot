package com.tradingbot.controller;

import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.websocket.PublicSubscriptionType;
import com.tradingbot.enums.websocket.RequestEventType;
import com.tradingbot.model.websocket.request.TradeEvent;
import com.tradingbot.service.KrakenService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.tradingbot.enums.RestApiEndpoints.PUBLIC_WEBSOCKET_URL;

@Controller
@Slf4j
public class StartUpController
{
    @Autowired
    private KrakenService krakenService;

    @PostConstruct
    public void subscribeOnStartup()
    {
        System.out.println("|=========================================|");
        System.out.println("| KRAKEN.COM JAVA TEST APP |");
        System.out.println("|=========================================|");
        System.out.println();
        try {
            krakenService.checkSystemStatus();
            krakenService.checkBalance();

            TradeEvent tradeEvent = new TradeEvent(RequestEventType.SUBSCRIBE, PublicSubscriptionType.OHLC_1, CurrencyPair.SOL_USD);
            krakenService.openAndStreamWebSocketSubscription(PUBLIC_WEBSOCKET_URL.getValue(), tradeEvent);
        } catch (Exception e)
        {
            log.error(e.getMessage());
        }
    }
}