package com.tradingbot.controller;

import com.tradingbot.enums.Currency;
import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.websocket.PublicSubscriptionType;
import com.tradingbot.enums.websocket.RequestEventType;
import com.tradingbot.model.rest.response.OHLCResponse;
import com.tradingbot.model.rest.response.SystemStatusResponse;
import com.tradingbot.model.websocket.request.TradeEvent;
import com.tradingbot.service.KrakenPrivateService;
import com.tradingbot.service.KrakenPublicService;
import com.tradingbot.service.KrakenWebsocketService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

import static com.tradingbot.enums.RestApiEndpoints.PUBLIC_WEBSOCKET_URL;

@Controller
@Slf4j
public class StartUpController
{
    @Autowired
    private KrakenWebsocketService websocketService;
    @Autowired
    private KrakenPublicService publicService;
    @Autowired
    private KrakenPrivateService privateService;

    @PostConstruct
    public void subscribeOnStartup()
    {
        log.info("|=========================================|");
        log.info("| KRAKEN.COM JAVA TEST APP |");
        log.info("|=========================================|");
        try {
            SystemStatusResponse systemStatus = publicService.getSystemStatus();
            log.info(systemStatus.toString());
            Map<Currency, Double> balance = privateService.getBalance();
            log.info(balance.toString());
            OHLCResponse candlesForInterval = publicService.getCandlesForInterval(CurrencyPair.SOL_USD, 1, 1);
            log.info(candlesForInterval.toString());


//            try {
//                AddOrderResponse addOrderResponse =
//                        privateService.AddOrder(OrderType.LIMIT, CurrencyPair.SOL_USD,100.4, OrderSide.BUY, 1.40555179);
//                log.info(addOrderResponse.toString());
//            }
//            catch (RuntimeException e)
//            {
//                log.error(e.getMessage());
//            }


            TradeEvent tradeEvent = new TradeEvent(RequestEventType.SUBSCRIBE, PublicSubscriptionType.OHLC_1, CurrencyPair.SOL_USD);
            websocketService.openAndStreamWebSocketSubscription(PUBLIC_WEBSOCKET_URL.getValue(), tradeEvent);
        } catch (Exception e)
        {
            log.error(e.getMessage());
        }
    }
}