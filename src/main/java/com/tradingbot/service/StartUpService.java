package com.tradingbot.service;

import com.tradingbot.enums.Currency;
import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.ResultStatus;
import com.tradingbot.enums.websocket.PublicSubscriptionType;
import com.tradingbot.enums.websocket.RequestEventType;
import com.tradingbot.model.rest.response.OHLCResponse;
import com.tradingbot.model.rest.response.SystemStatusResponse;
import com.tradingbot.model.websocket.request.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.tradingbot.enums.RestApiEndpoints.PUBLIC_WEBSOCKET_URL;

@Service
@Slf4j
public class StartUpService
{
    @Autowired
    private KrakenWebsocketService websocketService;
    @Autowired
    private KrakenPublicService publicService;
    @Autowired
    private KrakenPrivateService privateService;

    @EventListener(ApplicationReadyEvent.class)
    public void subscribeOnStartup()
    {
        log.info("|=========================================|");
        log.info("| KRAKEN.COM JAVA TEST APP |");
        log.info("|=========================================|");
        try {
            final SystemStatusResponse systemStatus = publicService.getSystemStatus();
            log.info(systemStatus.toString());
            if (systemStatus.getStatus() != ResultStatus.ONLINE)
                throw new RuntimeException("Kraken is not online. Check status at https://status.kraken.com/");
            final Map<Currency, Double> balance = privateService.getBalance();
            log.info(balance.toString());
            final OHLCResponse candlesForInterval = publicService.getCandlesForInterval(CurrencyPair.SOL_USD, 1, 1);
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