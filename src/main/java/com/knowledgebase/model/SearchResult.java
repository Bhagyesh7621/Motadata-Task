package com.knowledgebase.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class SearchResult {
    private String query;
    private String summaryAnswer;
    private List<KnowledgeArticle> relevantArticles;

    public SearchResult() {}

    public SearchResult(String query, String summaryAnswer, List<KnowledgeArticle> relevantArticles) {
        this.query = query;
        this.summaryAnswer = summaryAnswer;
        this.relevantArticles = relevantArticles;
    }

//    public String getQuery() { return query; }
//    public void setQuery(String query) { this.query = query; }
//
//    public String getSummaryAnswer() { return summaryAnswer; }
//    public void setSummaryAnswer(String summaryAnswer) { this.summaryAnswer = summaryAnswer; }
//
//    public List<KnowledgeArticle> getRelevantArticles() { return relevantArticles; }
//    public void setRelevantArticles(List<KnowledgeArticle> relevantArticles) {
//        this.relevantArticles = relevantArticles;
//    }
}