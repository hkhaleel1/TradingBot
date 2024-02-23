package com.tradingbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradingbot.listener.WebSocketEventListener;
import com.tradingbot.model.websocket.request.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class KrakenWebsocketService extends KrakenService
{
    public void openAndStreamWebSocketSubscription(String webSocketUrl, TradeEvent tradeEvent) throws InterruptedException, JsonProcessingException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final WebSocket ws = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create(webSocketUrl), new WebSocketEventListener(mapper, latch)).join();
        ws.sendText(mapper.toJson(tradeEvent), true);
        latch.await();
    }
}
