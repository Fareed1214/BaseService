package com.baseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baseservice.bo.BookInfo;
import com.baseservice.bo.Info;
import com.baseservice.bo.InputObject;
import com.baseservice.bo.LibraryView;
import com.baseservice.constants.ResponseStatus;
import com.baseservice.service.LibraryService;

@RestController
public class LibraryController
{

	@Autowired
	LibraryService service;

	@RequestMapping(value = "/baseservice/Addbook", method = RequestMethod.POST)
	public ResponseEntity<String> addBook(@RequestBody InputObject input)
	{		
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String result = service.addBook(input);
		if (result.equals(ResponseStatus.ADD_BOOK_SUCCESS))
			status = HttpStatus.OK;
		return new ResponseEntity<String>(result, status);
	}
		
	@RequestMapping(value = "/baseservice/Removebook/{input}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeBook(@PathVariable("input") String input)
	{
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String result = service.removeBook(input);		
		if (result.equals(ResponseStatus.REMOVE_BOOK_SUCCESS))
			status = HttpStatus.OK;
		return new ResponseEntity<String>(result, status);
	}

	@GetMapping("/baseservice/Searchbook/{input}")
	public ResponseEntity<Info> searchBook(@PathVariable("input") String input)
	{
		HttpStatus status = HttpStatus.NO_CONTENT;
		Info result = service.searchBook(input);		
		if (result != null)
			status = HttpStatus.OK;
		return new ResponseEntity<Info>(result, status);
	}

	@GetMapping("/baseservice/Searchbooks")
	public ResponseEntity<LibraryView> searchBooks()
	{
		HttpStatus status = HttpStatus.NO_CONTENT;
		LibraryView result = service.searchBooks();		
		if (result != null)
			status = HttpStatus.OK;
		return new ResponseEntity<LibraryView>(result, status);
	}
	
	@GetMapping("/baseservice/IsAvailable/{input}")
	public ResponseEntity<String> isAvailable(@PathVariable("input") String input)
	{
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String result = service.isAvailable(input);		
		if (result.equals(ResponseStatus.IS_BOOK_AVAILABLE_SUCCESS))
			status = HttpStatus.OK;
		return new ResponseEntity<String>(result, status);
	}

}
