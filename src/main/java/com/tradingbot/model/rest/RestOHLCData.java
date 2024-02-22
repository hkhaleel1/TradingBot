package com.tradingbot.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class RestOHLCData
{
    private int time;
    private double open;
    private double high;
    private double low;
    private double close;
    private double vwap;
    private double volume;
    private int count;

    public RestOHLCData (final List<Object> data)
    {
        this.time = (Integer)data.get(0);
        this.open = Double.parseDouble((String)data.get(1));
        this.high = Double.parseDouble((String)data.get(2));
        this.low = Double.parseDouble((String)data.get(3));
        this.close = Double.parseDouble((String)data.get(4));
        this.vwap = Double.parseDouble((String)data.get(5));
        this.volume = Double.parseDouble((String)data.get(6));
        this.count = (Integer)data.get(7);
    }
}