package com.example.demo.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.YoutubeMetadata;
import com.example.demo.services.ApiKeyService;
import com.example.demo.services.YTMetadataService;

@RestController
public class YTController {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private YTMetadataService yt_service;
	@Autowired
	private ApiKeyService key_service;
	
	@GetMapping("/get")
	public Page<YoutubeMetadata> getFirstPageData(){
		return yt_service.getYTMetadata(0);
	}
	@GetMapping("/{pageNo}")
	private Page<YoutubeMetadata> getNextPagesData(@PathVariable int pageNo){
		return yt_service.getYTMetadata(pageNo);
	}
	
	
	@GetMapping("/add-key")
	private void addApiKey(@RequestParam String key) {
		key_service.addNewKey(key);
	}
	
	@GetMapping("/search")
	private List<YoutubeMetadata> searchController(@RequestParam(name="query") String searchString) {
		FullTextEntityManager fullTextEntityManager 
		  = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory() 
				  .buildQueryBuilder()
				  .forEntity(YoutubeMetadata.class)
				  .get();
		org.apache.lucene.search.Query query = queryBuilder
				  .keyword()
				  .onFields("title","description")
				  .matching(searchString)
				  .createQuery();
		org.hibernate.search.jpa.FullTextQuery jpaQuery
		  = fullTextEntityManager.createFullTextQuery(query, YoutubeMetadata.class);
		return jpaQuery.getResultList();
	}
	
}
