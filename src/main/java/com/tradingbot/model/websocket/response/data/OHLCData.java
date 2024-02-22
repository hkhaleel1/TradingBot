package com.tradingbot.model.websocket.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    public OHLCData(final List<Object> data)
    {
        this.time = Double.parseDouble((String)data.get(0));
        this.etime = Double.parseDouble((String)data.get(1));
        this.open = Double.parseDouble((String)data.get(2));
        this.high = Double.parseDouble((String)data.get(3));
        this.low = Double.parseDouble((String)data.get(4));
        this.close = Double.parseDouble((String)data.get(5));
        this.vwap = Double.parseDouble((String)data.get(6));
        this.volume = Double.parseDouble((String)data.get(7));
        this.close = (Integer)data.get(8);
    }
}
