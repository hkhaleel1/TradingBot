package com.tradingbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiResponse<T>
{
    private String[] error;
    private T result;

    @Override
    public String toString() {
        if(getError().length > 0)
        {
            return "error=" + Arrays.toString(error);
        }
        return "result= " + result ;
    }
}
