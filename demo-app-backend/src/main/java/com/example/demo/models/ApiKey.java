package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Api Key Data model form ORM

@Entity
@Table
public class ApiKey {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String apiKey;

	public String getKey() {
		return apiKey;
	}

	public void setKey(String key) {
		apiKey = key;
	}

	public Long getId() {
		return id;
	}

}
