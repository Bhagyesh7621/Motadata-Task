package com.knowledgebase.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search-query")
public class wiki {

    private final RestTemplate restTemplate;

    public wiki() {
        this.restTemplate = new RestTemplate();

        // Add interceptor to set User-Agent
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("User-Agent", "MyKnowledgeBaseApp/1.0 (contact@example.com)");
            return execution.execute(request, body);
        });
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> searchQuery(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        String apiUrl = "https://en.wikipedia.org/api/rest_v1/page/summary/" + query.replace(" ", "_");

        Map<String, Object> externalResponse = restTemplate.getForObject(apiUrl, Map.class);

        String summary = (String) externalResponse.getOrDefault("extract", "No summary found.");

        Map<String, Object> response = new HashMap<>();
        response.put("query", query);
        response.put("ai_summary_answer", summary);

        return ResponseEntity.ok(response);
    }
}
