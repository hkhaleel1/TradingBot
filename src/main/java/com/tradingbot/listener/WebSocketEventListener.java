package com.tradingbot.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tradingbot.enums.websocket.PublicSubscriptionType;
import com.tradingbot.model.websocket.response.*;
import com.tradingbot.utils.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.http.WebSocket;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
@Slf4j
public class WebSocketEventListener implements WebSocket.Listener
{
    @Autowired
    private JsonMapper mapper;

    private final CountDownLatch latch;
    @Override
    public void onOpen(WebSocket webSocket)
    {
        log.info("WebSocket connection opened. Subprotocol: {}", webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last)
    {
        try {
            handleWebSocketMessage(data.toString());
        } catch (Exception e) {
            log.error("Error handling WebSocket message: {}", e.getMessage());
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    private void handleWebSocketMessage(final CharSequence data) throws JsonProcessingException
    {
        final JsonNode jsonNode = mapper.getTree(data.toString());
        if (jsonNode.has("connectionID"))
        {
            final SystemStatusMessage systemStatusMessage = mapper.fromJson(data.toString(), SystemStatusMessage.class);
            log.info(systemStatusMessage.toString());
        }
        else if(jsonNode.has("channelID"))
        {
            final SubscriptionStatusMessage<?> subscriptionStatusMessage =  mapper.fromJson(data.toString(),
                    SubscriptionStatusMessage.class);
            log.info(subscriptionStatusMessage.toString());
        }
        else if(jsonNode.has("event"))
        {
            final HeartbeatMessage heartbeatMessage =  mapper.fromJson(data.toString(), HeartbeatMessage.class);
            log.trace(heartbeatMessage.toString());
        }
        else if(jsonNode.get(0).isNumber())
        {
            final String channelName = jsonNode.get(2).asText();
            final PublicSubscriptionType subscriptionType = PublicSubscriptionType.fromChannelName(channelName);
            final PublicMessage<?> message =
                    Objects.equals(subscriptionType.getValue(), PublicSubscriptionType.OHLC_1.getValue()) ?
                            mapper.fromJson(data.toString(), OHLCMessage.class) :
                            mapper.fromJson(data.toString(), TradeMessage.class);

            log.info(message.toString());
        } else
        {
            log.warn("No mapping for message: {}", data);
        }
    }
}