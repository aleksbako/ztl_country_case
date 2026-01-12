package com.ztl.test.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class ZTLWebClient {

    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public <T> List<T> getEntityList(
            String endpoint,
            Map<String, String> headers,
            Class<T> elementType
    ) {
        return webClient.get()
                .uri(endpoint)
                .headers(h -> addHeaders(h, headers))
                .retrieve()
                .bodyToFlux(elementType)
                .collectList()
                .block();
    }


    private static void addHeaders(HttpHeaders httpHeaders, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
    }

}
