package com.leofandrade.githubuseractivity.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.io.IOException;

public class GitHubApiClient {

    private HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String getUserEvents(String username) throws IOException, InterruptedException {
        String url = "https://api.github.com/users/" + username + "/events";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Java-GitHub-CLI")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error fetching data from GitHub. HTTP Status: " + response.statusCode());
        }

        return response.body();

    }

}
