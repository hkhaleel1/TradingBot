package com.tradingbot.model.websocket.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tradingbot.model.websocket.response.data.OHLCData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class OHLCMessage extends PublicMessage<OHLCData>
{
    private String channelName;

    @JsonCreator
    public OHLCMessage(List<Object> values)
    {
        this.channelId = (Integer) values.get(0);
        this.data = mapData((List<Object>) values.get(1));
        this.channelName = (String) values.get(2);
        this.pair = (String) values.get(3);
    }

    @Override
    protected OHLCData mapData(List<Object> data) {
        return new OHLCData(
                Double.parseDouble((String)data.get(0)),
                Double.parseDouble((String)data.get(1)),
                Double.parseDouble((String)data.get(2)),
                Double.parseDouble((String)data.get(3)),
                Double.parseDouble((String)data.get(4)),
                Double.parseDouble((String)data.get(5)),
                Double.parseDouble((String)data.get(6)),
                Double.parseDouble((String)data.get(7)),
                (Integer)data.get(8)
                );
    }
}