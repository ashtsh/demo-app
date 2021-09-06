package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.ApiKey;
import com.example.demo.models.YoutubeMetadata;
import com.example.demo.repositories.YTMetadataRepository;

@Service
public class YTMetadataService {
	
	@Autowired
	private YTMetadataRepository yt_repository;
	@Autowired
	private ApiKeyService key_service;
	
	private String KEY = "";
	private ApiKey apiKey = null;
	private LocalDateTime prevDateTime= null;
	public Page<YoutubeMetadata> getYTMetadata(int pageNo){
		Pageable page = PageRequest.of(pageNo,5);
		Page<YoutubeMetadata> pagedData = yt_repository.findAllByOrderByPublishTimeDesc(page);
		return pagedData;
	}
	
	
	@Scheduled(fixedRate=11000)
	public void saveYTMetadata() {
//		final String KEY = "AIzaSyBeYv6bUidkNFKviMKXcpwGDLiR3m6hlVg";
		
		int keyLength = KEY.length();
		
		if(keyLength == 0) {
			apiKey = key_service.getNewKey();
			//No keys left
			if(apiKey == null) return;
 			KEY = apiKey.getKey();
		}
		
		
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
		
		if(prevDateTime == null)
			prevDateTime = currentDateTime.minusDays(1);
	
		final String YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet"+
									"&q=football&key="+KEY+
									"&type=video&order=date&publishedAfter="+prevDateTime.toString()+"Z";
		
		System.out.println(prevDateTime+"-----");
		prevDateTime = currentDateTime;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
		response
		  = restTemplate.getForEntity(YOUTUBE_URL, String.class);
		}
		catch(HttpClientErrorException e){
			key_service.deleteKey(apiKey.getId());
			apiKey = null;
			KEY = "";
			System.out.println("Key Exhausted");
			return;
		}
	
		
		
		JSONObject jsonResponse = new JSONObject(response.getBody());
		JSONArray dataItems = jsonResponse.getJSONArray("items");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		 
		for(int i = 0; i < dataItems.length(); i++) {
			YoutubeMetadata yt_metadata = new YoutubeMetadata();
			JSONObject snippetObject = dataItems.getJSONObject(i).getJSONObject("snippet");
			String video_id = dataItems.getJSONObject(i).getJSONObject("id").getString("videoId");
			if(!yt_repository.findById(video_id).isPresent()) {
				yt_metadata.setId(video_id);	
				yt_metadata.setTitle(snippetObject.getString("title"));
				yt_metadata.setDescription(snippetObject.getString("description"));
				yt_metadata.setChannelTitle(snippetObject.getString("channelTitle"));
				yt_metadata.setThumbnailUrl(snippetObject.getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
				LocalDateTime publishDateTime = LocalDateTime.parse(snippetObject.getString("publishTime"), dateTimeFormatter);
				yt_metadata.setPublishTime(publishDateTime);
				yt_metadata.setUTCPublishTimeString(snippetObject.getString("publishTime"));
				yt_repository.save(yt_metadata);

			}
		}
	
	}
}
