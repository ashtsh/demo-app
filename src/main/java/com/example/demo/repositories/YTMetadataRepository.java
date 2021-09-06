package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.YoutubeMetadata;

public interface YTMetadataRepository extends JpaRepository<YoutubeMetadata,Long> {
	  //  @Query(value="select * from youtube_metadata order by publish_time desc",nativeQuery=true)
		Page<YoutubeMetadata> findAllByOrderByPublishTimeDesc(Pageable page);
		Optional<YoutubeMetadata> findById(String id);
}
