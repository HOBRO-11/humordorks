package com.mal.humordorks.search;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@WebFluxTest
public class WebClientTest {

    @Test
    void main() throws InterruptedException {
        // Elasticsearch URL
        String url = "http://localhost:9201/test_food_item/_search";

        // JSON body
        String jsonBody = "{ " +
                "\"size\": 0, " +
                "\"aggs\": { " +
                "   \"test_food_terms\": { " +
                "       \"terms\": { " +
                "           \"field\": \"description\", " +
                "           \"size\": 10000, " +
                "           \"order\": { \"_key\": \"asc\" } " +
                "       } " +
                "   } " +
                "} " +
                "} ";

        // Create a WebClient instance
        WebClient webClient = WebClient.create();

        // Send POST request to Elasticsearch
        Mono<String> responseMono = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(jsonBody))
                .retrieve()
                .bodyToMono(String.class);

        // Subscribe to the response
        responseMono.subscribe(
                response -> {
                    System.out.println("Response from Elasticsearch:");
                    System.out.println(response);
                },
                error -> {
                    System.err.println("Error occurred while making request: " + error.getMessage());
                }
        );

        Thread.sleep(3000);
        System.out.println("--------------------------end----------------------");
    }

}
