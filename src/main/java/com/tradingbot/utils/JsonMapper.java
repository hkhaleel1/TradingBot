package com.tradingbot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingbot.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class JsonMapper
{
    final ObjectMapper jsonMapper = new ObjectMapper();
    public <T> ApiResponse<T> parseApiResponse(final String jsonResponse, final TypeReference<ApiResponse<T>> typeReference) throws JsonProcessingException
    {
        return jsonMapper.readValue(jsonResponse, typeReference);
    }

    public String toJson(final Object obj) throws JsonProcessingException
    {
        return jsonMapper.writeValueAsString(obj);
    }

    public JsonNode getTree(final String data) throws JsonProcessingException
    {
        return jsonMapper.readTree(data.toString());
    }

     public <T> T fromJson(final String json, Class<T> cls) throws JsonProcessingException
     {
        return jsonMapper.readValue(json, cls);
    }
}
