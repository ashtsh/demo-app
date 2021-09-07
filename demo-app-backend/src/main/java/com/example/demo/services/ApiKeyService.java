package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.ApiKey;
import com.example.demo.repositories.ApiKeyRepository;

//Service layer to recieve requests from controller and directs to jpa repository

@Service
public class ApiKeyService {
	
	@Autowired
	private ApiKeyRepository apiKeyRepo;
	
	public void addNewKey(String key) {
		ApiKey keyModel = new ApiKey();
		keyModel.setKey(key);
		apiKeyRepo.save(keyModel);
	}
	
	public ApiKey getNewKey() {
		 ApiKey a = apiKeyRepo.findAnyKey();
		 return a;
	}
	
	public void deleteKey(long id) {
		apiKeyRepo.deleteById(id);
	}

}
