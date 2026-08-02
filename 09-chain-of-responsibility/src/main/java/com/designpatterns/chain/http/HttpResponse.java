package com.designpatterns.chain.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP响应对象
 */
public class HttpResponse {
    
    private int statusCode = 200;
    private String statusMessage = "OK";
    private Map<String, String> headers;
    private String body;
    
    public HttpResponse() {
        this.headers = new HashMap<>();
    }
    
    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }
    
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
    
    public String getBody() {
        return body;
    }
    
    @Override
    public String toString() {
        return "HTTP " + statusCode + " " + statusMessage + 
               (body != null ? "\nBody: " + body : "");
    }
}
