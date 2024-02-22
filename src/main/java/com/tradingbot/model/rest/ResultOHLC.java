package com.tradingbot.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tradingbot.model.websocket.response.data.OHLCData;
import com.tradingbot.utils.JsonMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
public class ResultOHLC
{
    private List<RestOHLCData> data;
    private int last;

    @JsonCreator
    public ResultOHLC(Map<String, Object> values)
    {
        this.data = new ArrayList<>();
        values.forEach((key, value) -> {
            if (!key.equals("last"))
            {
                for(List<Object> ohlcData: (List<List<Object>>)value)
                {
                    this.data.add(new RestOHLCData(ohlcData));
                }
            } else {
                this.last = (int) value;
            }
        });
    }
}
