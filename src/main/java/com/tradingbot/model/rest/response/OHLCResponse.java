package com.tradingbot.model.rest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tradingbot.model.rest.response.data.RestOHLCData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
public class OHLCResponse
{
    private List<RestOHLCData> data;
    private int last;

    @JsonCreator
    public OHLCResponse(Map<String, Object> values)
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
