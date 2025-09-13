package com.knowledgebase.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "search_queries")
public class SearchQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String query;

    @Column(name = "search_timestamp", nullable = false)
    private LocalDateTime timestamp;

    public SearchQuery() {
        this.timestamp = LocalDateTime.now();
    }

    public SearchQuery(String query) {
        this();
        this.query = query;
    }
}
