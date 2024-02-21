package com.tradingbot.model.websocket.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OHLCData
{
    private double time;
    private double etime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double vwap;
    private double volume;
    private int count;
}