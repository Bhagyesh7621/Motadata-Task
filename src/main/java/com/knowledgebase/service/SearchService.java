package com.knowledgebase.service;

import com.knowledgebase.model.SearchQuery;
import com.knowledgebase.model.SearchResult;
import com.knowledgebase.repository.SearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    private final DuckDuckGoService duckDuckGoService;
    private final SearchRepository searchRepository;

    public SearchService(DuckDuckGoService duckDuckGoService,
                         SearchRepository searchRepository) {
        this.duckDuckGoService = duckDuckGoService;
        this.searchRepository = searchRepository;
    }

    public SearchResult processSearch(String query) {
        try {
            // Save the search query
            SearchQuery searchQuery = new SearchQuery(query);
            searchRepository.save(searchQuery);

            // Get results from DuckDuckGo API
            SearchResult result = duckDuckGoService.search(query);

            return result;
        } catch (Exception e) {
            logger.error("Error processing search: {}", e.getMessage(), e);
            return duckDuckGoService.getFallbackResult(query);
        }
    }
}