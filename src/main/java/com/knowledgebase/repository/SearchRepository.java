package com.knowledgebase.repository;

import com.knowledgebase.model.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<SearchQuery, Long> {
}