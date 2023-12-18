package com.example.exception;

import java.net.MalformedURLException;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = 1L;

	public StorageFileNotFoundException(String message, MalformedURLException e) {
		super(message);
	}

	public StorageFileNotFoundException(String message) {
		super(message);
	}
}
