package com.example.demo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


@Entity
@Indexed
@Table(indexes=@Index(name="publish_date_index",columnList="publishTime desc"))
public class YoutubeMetadata {
	@Id
	private String id;
	@Field
	private String title;
	@Field
	private String description;
	private String channelTitle;
	private LocalDateTime publishTime;
	private String thumbnailUrl;
	private String UTCPublishTimeString;
	public void setId(String id) {
		this.id = id;
	}

	public String getUTCPublishTimeString() {
		return UTCPublishTimeString;
	}

	public void setUTCPublishTimeString(String uTCPublishTimeString) {
		UTCPublishTimeString = uTCPublishTimeString;
	}


	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getChannelTitle() {
		return channelTitle;
	}
	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	public LocalDateTime getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(LocalDateTime publishTime) {
		this.publishTime = publishTime;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	
	public String getId() {
		return id;
	}
	
	
}
