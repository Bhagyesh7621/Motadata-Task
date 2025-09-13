package com.knowledgebase.service;

import com.knowledgebase.model.KnowledgeArticle;
import com.knowledgebase.model.SearchResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DuckDuckGoService {
    private static final String DUCKDUCKGO_API_URL = "http://api.duckduckgo.com/";
    private final RestTemplate restTemplate;

    public DuckDuckGoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SearchResult search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new SearchResult(
                    query,
                    "Query cannot be empty. Please enter a search term.",
                    new ArrayList<>()
            );
        }
        String url = UriComponentsBuilder.fromHttpUrl(DUCKDUCKGO_API_URL)
                .queryParam("q", query.trim())
                .queryParam("format", "json")
                .queryParam("no_html", "1")
                .queryParam("skip_disambig", "1")
                .toUriString();
        try{
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            System.out.println("response: " + response);

            String abstractText = (String) response.get("Abstract");
            String abstractSource = (String) response.get("AbstractSource");
            String abstractURL = (String) response.get("AbstractURL");

            String summaryAnswer = abstractText != null && !abstractText.isEmpty()
                    ? abstractText
                    : "No specific information found. Try rephrasing your question or be more specific.";

            List<KnowledgeArticle> articles = new ArrayList<>();
            if (abstractText != null && !abstractText.isEmpty()) {
                articles.add(new KnowledgeArticle(
                        abstractSource + ": " + query,
                        abstractText,
                        abstractURL
                ));
            }

            List<Map<String, Object>> relatedTopics = (List<Map<String, Object>>) response.get("RelatedTopics");
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}