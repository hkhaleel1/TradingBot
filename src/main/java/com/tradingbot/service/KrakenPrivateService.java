package com.tradingbot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tradingbot.config.KrakenProperties;
import com.tradingbot.enums.Currency;
import com.tradingbot.enums.CurrencyPair;
import com.tradingbot.enums.RestApiEndpoints;
import com.tradingbot.enums.rest.OrderSide;
import com.tradingbot.enums.rest.OrderType;
import com.tradingbot.model.rest.request.AddOrder;
import com.tradingbot.model.rest.request.Balance;
import com.tradingbot.model.rest.response.AddOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import static com.tradingbot.enums.RestApiEndpoints.*;

@Service
@Slf4j
public class KrakenPrivateService extends KrakenService
{
    @Autowired
    private KrakenProperties properties;

    public Map<Currency, Double> getBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException
    {
        final String nonce = String.valueOf(System.currentTimeMillis());
        final String apiPostBodyData = new Balance(nonce).toApiPostBodyData();
        final String response = getResponse(BALANCE, nonce, apiPostBodyData);
        return mapper.parseApiResponse(response, new TypeReference<>() {});
    }

    public AddOrderResponse AddOrder(final OrderType type,
                                     final CurrencyPair pair,
                                     final double price,
                                     final OrderSide side,
                                     final double volume) throws NoSuchAlgorithmException, InvalidKeyException, IOException
    {
        if(!properties.isModifyOrders())
        {
            throw new RuntimeException("Cannot Add order. Set modify.orders to true.");
        }
        final String nonce = String.valueOf(System.currentTimeMillis());
        final String apiPostBodyData = new AddOrder(nonce, type, pair, price, side, volume).toApiPostBodyData();
        final String response = getResponse(ADD_ORDER, nonce, apiPostBodyData);
        return mapper.parseApiResponse(response, new TypeReference<>() {});
    }

    private String getResponse(RestApiEndpoints endpoint, String nonce, String body) throws IOException ,NoSuchAlgorithmException, InvalidKeyException
    {
        if (properties.getApiPrivateKey() == null || properties.getApiPrivateKey().isEmpty()
                || properties.getApiPublicKey() ==  null || properties.getApiPublicKey().isEmpty())
        {
            throw new IllegalArgumentException("Private or public key missing!");
        }
        final String url = BASE_DOMAIN.getValue() + PRIVATE_ENDPOINT.getValue() + endpoint.getValue();

        final String signature = CreateAuthenticationSignature(PRIVATE_ENDPOINT.getValue(), endpoint.getValue(), nonce, body);

        // CREATE HTTP CONNECTION
        final URL apiUrl = new URL(url);
        final HttpsURLConnection httpConnection = (HttpsURLConnection) apiUrl.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("API-Key", properties.getApiPublicKey());
        httpConnection.setRequestProperty("API-Sign", signature);
        httpConnection.setDoOutput(true);
        final DataOutputStream os = new DataOutputStream(httpConnection.getOutputStream());
        os.writeBytes(body);
        os.flush();
        os.close();

        // GET JSON RESPONSE DATA
        final StringBuilder responseJson = new StringBuilder();
        final BufferedReader br = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String line;
        while ((line = br.readLine()) != null) {
            responseJson.append(line);
        }
        return responseJson.toString();
    }




    /** Authentication Algorithm */
    private String CreateAuthenticationSignature(final String apiPath,
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
        mac.init(new SecretKeySpec(Base64.getDecoder().decode(properties.getApiPrivateKey().getBytes()), "HmacSHA512"));
        mac.update((apiPath + endPointName).getBytes());

        // CREATE API SIGNATURE
        return Base64.getEncoder().encodeToString(mac.doFinal(sha256Hash));
    }
}
