package com.baseservice.service;

import org.springframework.stereotype.Service;

import com.baseservice.bo.Info;
import com.baseservice.bo.InputObject;
import com.baseservice.bo.LibraryView;

@Service
public interface LibraryService
{
	String addBook(InputObject input);

	String removeBook(String input);

	Info searchBook(String input);

	LibraryView searchBooks();
	
	String isAvailable(String input);

}
