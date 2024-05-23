package com.example.task.Controller;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;
import com.example.task.log.LoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

public class RESTClientController {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RESTClientController().restTemplate();
        String result = restTemplate.getForObject("http://localhost:8080/contact/restClient", String.class);
        System.out.println(result);
    }
}