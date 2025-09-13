package com.knowledgebase.controller;

import com.knowledgebase.model.SearchResult;
import com.knowledgebase.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/searchquery")
    public ResponseEntity<SearchResult> searchQuery(@RequestBody SearchRequest request) {
        try {
            SearchResult result = searchService.processSearch(request.getQuery());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    public static class SearchRequest {
        private String query;

        public String getQuery() { return query; }
        public void setQuery(String query) { this.query = query; }
    }
}