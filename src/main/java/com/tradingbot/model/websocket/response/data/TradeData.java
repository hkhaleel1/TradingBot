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

    public static TradeData fromList(final List<String> values)
    {
        return new TradeData(
                Double.parseDouble(values.get(0)),
                Double.parseDouble(values.get(1)),
                Double.parseDouble(values.get(2)),
                TriggeringOrderSide.fromValue(values.get(3)),
                TriggeringOrderType.fromValue(values.get(4)),
                values.get(5)
        );
    }
}
