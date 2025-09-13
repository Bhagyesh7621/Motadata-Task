package com.knowledgebase.service;

import com.knowledgebase.model.KnowledgeArticle;
import com.knowledgebase.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DuckDuckGoService {
    private static final Logger logger = LoggerFactory.getLogger(DuckDuckGoService.class);
    private static final String DUCKDUCKGO_API_URL = "https://api.duckduckgo.com/";
    private final RestTemplate restTemplate;

    public DuckDuckGoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SearchResult search(String query) {
        try {
            // Build the API URL
            String url = UriComponentsBuilder.fromHttpUrl(DUCKDUCKGO_API_URL)
                    .queryParam("q", query)
                    .queryParam("format", "json")
                    .queryParam("no_html", "1")
                    .queryParam("skip_disambig", "1")
                    .toUriString();

            logger.info("Making request to DuckDuckGo API: {}", url);

            // Call the API with proper error handling
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                logger.warn("DuckDuckGo API returned non-OK status: {}", response.getStatusCode());
                return getFallbackResult(query);
            }

            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null) {
                logger.warn("DuckDuckGo API returned null response body");
                return getFallbackResult(query);
            }

            // Process the response
            String abstractText = (String) responseBody.get("Abstract");
            String abstractSource = (String) responseBody.get("AbstractSource");
            String abstractURL = (String) responseBody.get("AbstractURL");

            // Create a summary answer
            String summaryAnswer = abstractText != null && !abstractText.isEmpty()
                    ? abstractText
                    : "No specific information found. Try rephrasing your question or be more specific.";

            // Create relevant articles list
            List<KnowledgeArticle> articles = new ArrayList<>();
            if (abstractText != null && !abstractText.isEmpty()) {
                articles.add(new KnowledgeArticle(
                        abstractSource + ": " + query,
                        abstractText,
                        abstractURL
                ));
            }

            // Add related topics if available
            List<Map<String, Object>> relatedTopics = (List<Map<String, Object>>) responseBody.get("RelatedTopics");
            if (relatedTopics != null) {
                for (Map<String, Object> topic : relatedTopics) {
                    String text = (String) topic.get("Text");
                    if (text != null && !text.isEmpty()) {
                        String firstURL = (String) topic.get("FirstURL");
                        articles.add(new KnowledgeArticle(
                                text.length() > 50 ? text.substring(0, 50) + "..." : text,
                                text,
                                firstURL != null ? firstURL : ""
                        ));
                    }
                }
            }

            return new SearchResult(query, summaryAnswer, articles);

        } catch (Exception e) {
            logger.error("Error calling DuckDuckGo API: {}", e.getMessage(), e);
            return getFallbackResult(query);
        }
    }

    SearchResult getFallbackResult(String query) {
        // Fallback to hardcoded responses for common IT questions
        Map<String, String> faq = new HashMap<>();
        faq.put("what is rabbitmq", "RabbitMQ is an open-source message-broker software that originally implemented the Advanced Message Queuing Protocol (AMQP).");
        faq.put("how to install java", "To install Java, you can download the JDK from Oracle's website or use package managers like apt for Ubuntu or Homebrew for Mac.");
        faq.put("what is spring boot", "Spring Boot is an open-source Java-based framework used to create a microservice. It is developed by Pivotal Team.");
        // Add more FAQs as needed

        String lowerQuery = query.toLowerCase();
        String summaryAnswer = faq.getOrDefault(lowerQuery, "Sorry, I couldn't find information about that. Please try another question or check the spelling.");

        List<KnowledgeArticle> articles = new ArrayList<>();
        articles.add(new KnowledgeArticle("Fallback Answer", summaryAnswer, ""));

        return new SearchResult(query, summaryAnswer, articles);
    }
}