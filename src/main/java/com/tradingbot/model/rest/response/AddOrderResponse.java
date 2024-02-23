package com.tradingbot.model.rest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tradingbot.model.rest.response.data.OrderData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
public class AddOrderResponse
{
    private String txId;
    private OrderData data;

    @JsonCreator
    public AddOrderResponse(final Map<String, Object> values)
    {
        this.txId = (String)((ArrayList<?>)values.get("txid")).get(0);
        this.data = new OrderData((String)((Map<?, ?>)values.get("descr")).get("order"));
    }
}
