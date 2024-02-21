package com.tradingbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyPair
{
    SOL_USD(Currency.SOL, Currency.USD)
    ;
    private final Currency value1;
    private final Currency value2;
    private static final String DELIMITER = "/";

    public String toString()
    {
        return value1 + DELIMITER + value2;
    }
}
