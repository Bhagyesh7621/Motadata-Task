package com.knowledgebase.service;

import com.knowledgebase.model.SearchQuery;
import com.knowledgebase.model.SearchResult;
import com.knowledgebase.repository.SearchRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final DuckDuckGoService duckDuckGoService;
    private final SearchRepository searchRepository;

    public SearchService(DuckDuckGoService duckDuckGoService,
                         SearchRepository searchRepository) {
        this.duckDuckGoService = duckDuckGoService;
        this.searchRepository = searchRepository;
    }

    public SearchResult processSearch(String query) {
        // Save the search query
        SearchQuery searchQuery = new SearchQuery(query);
        searchRepository.save(searchQuery);
        System.out.println("Request saved in DB");
        // Get results from DuckDuckGo API
        SearchResult result = duckDuckGoService.search(query);
        System.out.println("Got Result");
        return result;
    }
}