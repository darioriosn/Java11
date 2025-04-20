package com.httpapi.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Create an HTTP client
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Build a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/octocat"))
                .header("Accept", "application/json")
                .GET()
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // Print the response status and body
        System.out.println("Status code: " + response.statusCode());
        System.out.println("Body: " + response.body());
    }
}
