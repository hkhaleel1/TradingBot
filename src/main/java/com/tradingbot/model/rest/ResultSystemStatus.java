package com.tradingbot.model.rest;

import com.tradingbot.enums.ResultStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
public class ResultSystemStatus
{
    private ResultStatus status;
    private Date timestamp;
    @Override
    public String toString() {
        return status + " @ " + timestamp;
    }
}
