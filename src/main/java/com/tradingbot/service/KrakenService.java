package com.tradingbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingbot.enums.Currency;
import com.tradingbot.listener.WebSocketEventListener;
import com.tradingbot.model.ApiResponse;
import com.tradingbot.model.ResultSystemStatus;
import com.tradingbot.model.websocket.request.TradeEvent;
import com.tradingbot.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
import java.net.http.WebSocket;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static com.tradingbot.enums.RestApiEndpoints.*;

@Service
@Slf4j
public class KrakenService
{
    @Autowired
    private JsonMapper mapper;

    @Value("${kraken.apiPublicKey}")
    private String apiPublicKey;
    @Value("${kraken.apiPrivateKey}")
    private String apiPrivateKey;

    public void checkSystemStatus() throws ExecutionException, InterruptedException, JsonProcessingException
    {
        final String apiEndpointFullURL = BASE_DOMAIN.getValue() + PUBLIC_ENDPOINT.getValue() + SYSTEM_STATUS.getValue();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiEndpointFullURL)).build();
        final CompletableFuture<String> publicResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);

        while (!publicResponse.isDone()) {
            // WAIT
        }

        final ApiResponse<ResultSystemStatus> response = mapper.parseApiResponse(publicResponse.get(),
                new TypeReference<>() {});
        log.info(response.toString());
    }

    public void checkBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException
    {
        if (apiPrivateKey.isEmpty() || apiPublicKey.isEmpty())
        {
            throw new IllegalArgumentException("Private or public key missing!");
        }
        final StringBuilder responseJson = new StringBuilder();
        final String apiEndpointFullURL = BASE_DOMAIN.getValue() + PRIVATE_ENDPOINT.getValue() + BALANCE.getValue();
        final String nonce = String.valueOf(System.currentTimeMillis());
        final String apiPostBodyData = "nonce=" + nonce;
        final String signature = CreateAuthenticationSignature(apiPrivateKey, PRIVATE_ENDPOINT.getValue(), BALANCE.getValue(), nonce, apiPostBodyData);

        // CREATE HTTP CONNECTION
        final URL apiUrl = new URL(apiEndpointFullURL);
        final HttpsURLConnection httpConnection = (HttpsURLConnection) apiUrl.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("API-Key", apiPublicKey);
        httpConnection.setRequestProperty("API-Sign", signature);
        httpConnection.setDoOutput(true);
        final DataOutputStream os = new DataOutputStream(httpConnection.getOutputStream());
        os.writeBytes(apiPostBodyData);
        os.flush();
        os.close();

        // GET JSON RESPONSE DATA
        final BufferedReader br = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String line;
        while ((line = br.readLine()) != null) {
            responseJson.append(line);
        }

        final ApiResponse<Map<Currency, Double>> privateResponse =
                mapper.parseApiResponse(responseJson.toString(), new TypeReference<>() {});

        if (privateResponse.getError().length > 0)
        {
            throw new RuntimeException(String.join("\n",privateResponse.getError()));
        }
        log.info(privateResponse.toString());
    }

    public void openAndStreamWebSocketSubscription(String webSocketUrl, TradeEvent tradeEvent) throws InterruptedException, JsonProcessingException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final WebSocket ws = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create(webSocketUrl), new WebSocketEventListener(mapper, latch)).join();
        ws.sendText(mapper.toJson(tradeEvent), true);
        latch.await();
    }

    /** Authentication Algorithm */
    private String CreateAuthenticationSignature(final String apiPrivateKey,
                                                 final String apiPath,
                                                 final String endPointName,
                                                 final String nonce,
                                                 final String apiPostBodyData) throws NoSuchAlgorithmException, InvalidKeyException
    {

        // GET 256 HASH
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((nonce + apiPostBodyData).getBytes());
        final byte[] sha256Hash = md.digest();

        // GET 512 HASH
        final Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(new SecretKeySpec(Base64.getDecoder().decode(apiPrivateKey.getBytes()), "HmacSHA512"));
        mac.update((apiPath + endPointName).getBytes());

        // CREATE API SIGNATURE
        return Base64.getEncoder().encodeToString(mac.doFinal(sha256Hash));
    }
}
