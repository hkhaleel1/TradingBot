package com.tradingbot.model.websocket.response.data;

import com.tradingbot.enums.websocket.TriggeringOrderSide;
import com.tradingbot.enums.websocket.TriggeringOrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TradeData
{
    private double price;
    private double volume;
    private double timeSinceEpoch;
    private TriggeringOrderSide triggeringOrderSide;
    private TriggeringOrderType triggeringOrderType;
    private String misc;

    public TradeData(final List<Object> data)
    {
        this.price = Double.parseDouble((String) data.get(0));
        this.volume = Double.parseDouble((String) data.get(1));
        this.timeSinceEpoch = Double.parseDouble((String) data.get(2));
        this.triggeringOrderSide = TriggeringOrderSide.fromValue((String) data.get(3));
        this.triggeringOrderType = TriggeringOrderType.fromValue((String) data.get(4));
        this.misc = (String) data.get(5);
    }
}
