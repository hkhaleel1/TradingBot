package com.tradingbot.model.rest.response.data;

import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.rest.OrderSide;
import com.tradingbot.enums.rest.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class OrderData
{
    //"order":"buy 1.40555179 SOLUSD @ limit 100.40"
    private OrderSide side;
    private double amount;
    private CurrencyPair pair;
    private OrderType type;
    private double price;

    public OrderData(final String order)
    {
        final String[] split = order.split(" ");
        this.side = OrderSide.valueOf(split[0].toUpperCase());
        this.amount = Double.parseDouble(split[1]);
        this.pair = CurrencyPair.fromString(split[2]);
        this.type = OrderType.valueOf(split[4].toUpperCase());
        this.price = Double.parseDouble(split[5]);
    }
}
