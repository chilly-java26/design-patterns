package com.designpatterns.chain.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求对象
 */
public class HttpRequest {
    
    private String method;
    private String uri;
    private Map<String, String> headers;
    private String body;
    
    public HttpRequest(String method, String uri) {
        this.method = method;
        this.uri = uri;
        this.headers = new HashMap<>();
    }
    
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    
    public String getHeader(String key) {
        return headers.get(key);
    }
    
    public String getMethod() {
        return method;
    }
    
    public String getUri() {
        return uri;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    @Override
    public String toString() {
        return method + " " + uri;
    }
}
