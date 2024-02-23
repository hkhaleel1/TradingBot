package com.tradingbot.model.rest.response;

import com.tradingbot.enums.ResultStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class SystemStatusResponse
{
    private ResultStatus status;
    private Date timestamp;
    @Override
    public String toString() {
        return status + " @ " + timestamp;
    }
}
