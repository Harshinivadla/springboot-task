package com.example.task.log;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request) {
        log.info("Log Request Details---");
        log.info("Request: {} {}", request.getMethod(), request.getURI());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("Log Response Details---");
        StringBuilder responseBody = new StringBuilder();
        HttpStatusCode statusCode = response.getStatusCode();
        try {
            if (response.getBody() != null) {
                responseBody.append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.error("Error reading response body", e);
        }
        log.info("Response: {} Status code: {}, Body: {}", response.getStatusText(), statusCode, responseBody);
    }
}