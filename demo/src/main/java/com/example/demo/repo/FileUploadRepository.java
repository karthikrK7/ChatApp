package com.example.demo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.FileUpload;

@Repository
public interface FileUploadRepository extends MongoRepository<FileUpload, String>, CollectionRepository {
	
	@Query("{ 'userId' : ?0 }")
	List<FileUpload> findByuserId(String userId);

}