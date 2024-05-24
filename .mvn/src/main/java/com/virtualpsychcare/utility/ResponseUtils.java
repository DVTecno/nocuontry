package com.virtualpsychcare.utility;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static Map<String, Object> createResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}
