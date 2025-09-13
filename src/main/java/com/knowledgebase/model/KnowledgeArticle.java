package com.knowledgebase.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KnowledgeArticle {
    private String title;
    private String content;
    private String url;

    public KnowledgeArticle() {}

    public KnowledgeArticle(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }
}