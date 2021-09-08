package com.sm.libraryservice.action;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sm.libraryservice.model.Book;

@RestController
@RequestMapping("/library")
public class LibraryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/books")
	public String getAllBooks(@RequestParam(required = false) String title) {
		logger.info("inside library-service books...");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity <String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("http://book-service/books", HttpMethod.GET, entity, String.class).getBody();
	
	}
	@PostMapping("/books")
	public String createProducts(@RequestBody Book book) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
	      
	    return restTemplate.exchange(
	       "http://book-service/books", HttpMethod.POST, entity, String.class).getBody();
	}
}
