package com.tradingbot.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultStatus
{
    ONLINE("online"),
    MAINTENANCE("maintenance"),
    CANCEL_ONLY("cancel_only"),
    POST_ONLY("post_only")
    ;
    private final String value;
    @JsonValue
    public String getEnumValue() {
        return value;
    }
}
