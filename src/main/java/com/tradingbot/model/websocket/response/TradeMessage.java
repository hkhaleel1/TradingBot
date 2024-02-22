package com.tradingbot.model.websocket.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tradingbot.model.websocket.response.data.TradeData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString(callSuper=true)
public class TradeMessage extends PublicMessage<List<TradeData>>
{
    private String event;

    @JsonCreator
    public TradeMessage(List<Object> values)
    {
        this.channelId = (Integer) values.get(0);
        this.data = mapDataList((List<List<Object>>) values.get(1));
        this.event = (String) values.get(2);
        this.pair = (String) values.get(3);
    }

    protected List<TradeData> mapDataList(List<List<Object>> data) {
        return data.stream()
                .map(TradeData::new)
                .toList();
    }
}
