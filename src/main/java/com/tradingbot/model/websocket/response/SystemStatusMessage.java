package com.tradingbot.model.websocket.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemStatusMessage
{
    private BigInteger connectionID;
    private String event;
    private String status;
    private String version;
}