package com.example.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void delete(String storageFilename) throws IOException;

	Path load(String filename);

	Resource loadAsResource(String storageFilename);

	void store(MultipartFile file, String storageFilename);

	String getStorageFilename(MultipartFile file, String id);

}
