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
        this.data = new OHLCData((List<Object>) values.get(1));
        this.channelName = (String) values.get(2);
        this.pair = (String) values.get(3);
    }
}
