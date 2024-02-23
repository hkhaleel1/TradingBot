package com.tradingbot.model.rest.request;

import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.rest.OrderSide;
import com.tradingbot.enums.rest.OrderType;
import lombok.Getter;

@Getter
public class AddOrder extends Request
{
    private OrderType type;
    private CurrencyPair pair;
    private double price;
    private OrderSide side;
    private double volume;

    public AddOrder(String nonce, OrderType type, CurrencyPair pair, double price, OrderSide side, double volume) {
        super(nonce);
        this.type = type;
        this.pair = pair;
        this.price = price;
        this.side = side;
        this.volume = volume;
    }

    @Override
    public String toApiPostBodyData()
    {
        return "nonce=" + nonce +
                "&ordertype=" + type.toString().toLowerCase() +
                "&pair=" + pair.toURIString() +
                "&price=" + price +
                "&type=" + side.toString().toLowerCase() +
                "&volume=" + volume;
    }
}
