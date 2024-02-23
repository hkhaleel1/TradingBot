package com.tradingbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

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
    public String toURIString()
    {
        return String.format("%s%s", value1, value2);
    }

    public static CurrencyPair fromString(final String pair)
    {
        Optional<CurrencyPair> opPair = Arrays.stream(CurrencyPair.values()).filter(x -> x.toURIString().equals(pair)).findFirst();
        if (opPair.isEmpty())
            throw new IllegalArgumentException("No pair found for: " + pair);
        return opPair.get();
    }
}
