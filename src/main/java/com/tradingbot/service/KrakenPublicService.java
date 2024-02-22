package com.tradingbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingbot.enums.Currency;
import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.model.rest.ResultSystemStatus;
import com.tradingbot.model.rest.ResultOHLC;
import com.tradingbot.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.tradingbot.enums.RestApiEndpoints.*;

@Service
@Slf4j
public class KrakenPublicService extends KrakenService
{
    public ResultSystemStatus getSystemStatus() throws ExecutionException, InterruptedException, JsonProcessingException
    {
        final String url = BASE_DOMAIN.getValue() + PUBLIC_ENDPOINT.getValue() + SYSTEM_STATUS.getValue();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        final CompletableFuture<String> publicResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        while (!publicResponse.isDone()) {
            // WAIT
        }
        return mapper.parseApiResponse(publicResponse.get(), new TypeReference<>() {});
    }

    public ResultOHLC getCandlesForInterval(final CurrencyPair pair,
                                      final int interval) throws ExecutionException, InterruptedException, JsonProcessingException
    {
        return getCandlesForInterval(pair, interval, 0);
    }

    public ResultOHLC getCandlesForInterval(final CurrencyPair pair,
                                      final int interval,
                                      final int numberOfCandles) throws ExecutionException, InterruptedException, JsonProcessingException
    {
        final String url = BASE_DOMAIN.getValue() + PUBLIC_ENDPOINT.getValue() + OHLC.getValue();
        String queryParameters = "?pair=" + pair.toURIString() + "&interval=" + interval;

        if (numberOfCandles < 0)
        {
            throw new IllegalArgumentException("Number of candles has to be greater than 0.");
        }
        else if (numberOfCandles > 0)
        {
            long currentTimeSeconds = System.currentTimeMillis() / 1000;
            long sinceSeconds = currentTimeSeconds - ((long) interval * 60 * numberOfCandles);
            queryParameters = queryParameters + "&since=" + sinceSeconds;
        }

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + queryParameters)).build();
        final CompletableFuture<String> publicResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        while (!publicResponse.isDone()) {
            // WAIT
        }
        return mapper.parseApiResponse(publicResponse.get(), new TypeReference<>() {});
    }
}
