package com.baseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baseservice.bo.BookInfo;
import com.baseservice.bo.Info;
import com.baseservice.bo.InputObject;
import com.baseservice.bo.LibraryView;
import com.baseservice.constants.ResponseStatus;

/**
 * service implementation for Library Service
 * 
 * @author farshaik4
 *
 */
@Service
public class LibraryServiceImpl implements LibraryService
{

	@Autowired
	RestTemplate restTemplate;

	@Value("${dataservice.url}")
	private String url;

	@Value("${dataservice.addbook}")
	private String addBook;

	@Value("${dataservice.removeBook}")
	private String removeBook;

	@Value("${dataservice.isAvailable}")
	private String isAvailable;

	@Value("${dataservice.searchBook}")
	private String searchBook;

	@Value("${dataservice.searchBooks}")
	private String searchBooks;

	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Override
	public String addBook(InputObject input)
	{
		String result = ResponseStatus.ADD_BOOK_FAIL;
		try {
			System.out.println("Base Service Request - addBook - " + input);
			if (input != null && input.getTitle() != null && !input.getTitle().isEmpty()
					&& input.getTitle().length() <= 200) {
				if (input.getAuthors() != null && (input.getAuthors().isEmpty() || input.getAuthors().length() > 200))
					return result;
				if (input.getCost() != null && (input.getCost().isEmpty() || input.getTitle().length() > 10
						|| input.getCost().contains("Special Chars or Letters")))
					return result;
				if (input.getDescription() != null
						&& (input.getDescription().isEmpty() || input.getDescription().length() > 2000))
					return result;
				if (input.getGenres() != null && (input.getGenres().isEmpty() || input.getGenres().length() > 50))
					return result;
				if (input.getNoOfCopies() == 0 || input.getTitle().length() > 10 || input.getCost().contains("Special Chars or Letters"))
					return result;
				if (input.getPublisher() != null && (input.getPublisher().isEmpty() || input.getPublisher().length() > 50))
					return result;
				Boolean response = restTemplate.postForObject(url + addBook, input, Boolean.class);
				if(response)
					result = ResponseStatus.ADD_BOOK_SUCCESS; 					
			}
			System.out.println("Base Service Request - addBook - " + result);
		} catch (Exception e) {
			return result = e.getMessage();
		}
		return result;
	}

	@Override
	public String removeBook(String input)
	{
		String result = ResponseStatus.REMOVE_BOOK_FAIL;
		try {
			if (input != null && !input.isEmpty() && input.length() <= 200) {
				System.out.println("Base Service Request - removeBook - " + input);
				restTemplate.delete(url + removeBook + input);
				System.out.println("Base Service Response - removeBook - Removed !");
				result = ResponseStatus.REMOVE_BOOK_SUCCESS;
			}
		} catch (Exception e) {
			return result = e.getMessage();
		}
		return result;

	}

	@Override
	public Info searchBook(String input)
	{
		Info info = new Info();
		info.setStatus(ResponseStatus.SEARCH_BOOK_FAIL);
		try {
			if (input != null && !input.isEmpty() && input.length() <= 200) {
				System.out.println("Base Service Request - searchBook - " + input);
				ResponseEntity responseEntity = restTemplate.getForEntity(url + searchBook + input, BookInfo.class);
				System.out.println("Base Service Response - searchBook - " + responseEntity.getBody().toString());
				Object obj = responseEntity.getBody();
				if (obj != null) {
					info = (BookInfo) responseEntity.getBody();
					info.setStatus(ResponseStatus.SEARCH_BOOK_SUCCESS);
				}
			}
		} catch (Exception e) {
			info.setStatus(e.getMessage());
		}
		return info;
	}

	@Override
	public LibraryView searchBooks()
	{
		LibraryView library = null;
		try {

			ResponseEntity responseEntity = restTemplate.getForEntity(url + searchBooks, LibraryView.class);
			library = (LibraryView) responseEntity.getBody();

			if (library == null) {
				library = new LibraryView();
				library.setResult(ResponseStatus.SEARCH_BOOKS_FAIL);
			}
		} catch (Exception e) {
			library.setResult(ResponseStatus.SEARCH_BOOKS_FAIL);
		}
		return library;
	}

	@Override
	public String isAvailable(String input)
	{
		String strIsAvaliale = ResponseStatus.IS_BOOK_AVAILABLE_FAIL;

		try {
			if (input != null && !input.isEmpty() && input.length() <= 200) {
				System.out.println("Base Service Request - isAvailable - " + input);
				ResponseEntity responseEntity = restTemplate.getForEntity(url + isAvailable + input, Boolean.class);
				System.out.println("Base Service Response - isAvailable - " + responseEntity.getBody());
				if(responseEntity.getBody().toString() == "true")
					strIsAvaliale = ResponseStatus.IS_BOOK_AVAILABLE_SUCCESS;
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return strIsAvaliale;
	}

}
