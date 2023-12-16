package com.example.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.config.StorageProperties;
import com.example.exception.StorageException;
import com.example.exception.StorageFileNotFoundException;
import com.example.service.StorageService;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
	private final Path rootLocation;

	@Override
	public String getStorageFilename(MultipartFile file, String id) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}

	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file, String storageFilename) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + storageFilename);
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(storageFilename)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Failed to store file " + storageFilename + ". Path is not valid.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Resource loadAsResource(String storageFilename) {
		try {
			Path file = load(storageFilename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("Could not read file: " + storageFilename);
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + storageFilename, e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public void delete(String storageFilename) throws IOException {
		Path file = rootLocation.resolve(Paths.get(storageFilename)).normalize().toAbsolutePath();
		Files.delete(file);
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
