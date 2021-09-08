package com.sm.bookservice.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.bookservice.dao.BookRepository;
import com.sm.bookservice.model.Book;

@RestController
@RefreshScope
public class BookController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	BookRepository bookRepository;
	
	//@Value("${my.greeting: Hello SM}")
	//private String greetingMsg;
	
	@GetMapping("/books")
	  public List<Book> getAllBooks(@RequestParam(required = false) String title) {
		logger.info("inside book-service books...");
	    try {
	      List<Book> books = new ArrayList<Book>();
	      if (title == null) {
	    	  books = bookRepository.findAll();
	      }
	    	 // bookRepository.findAll().forEach(books::add);
	      else
	    	  bookRepository.findByTitle(title).forEach(books::add);

	      /*if (books.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }*/

	      //return new ResponseEntity<>(books, HttpStatus.OK);
	      return books;
	    } catch (Exception e) {
	    	e.printStackTrace();
	      //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return null;
	  }

	  @GetMapping("/books/{id}")
	  public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
	    Optional<Book> bookData = bookRepository.findById(id);

	    if (bookData.isPresent()) {
	      return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @PostMapping("/books")
	  public ResponseEntity<Book> createBook(@RequestBody Book book) {
	    try {
	      Book _book = bookRepository
	          .save(new Book(book.getTitle(), book.getAuthor(), book.getPrice()));
	      return new ResponseEntity<>(_book, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @PutMapping("/books/{id}")
	  public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
	    Optional<Book> bookData = bookRepository.findById(id);

	    if (bookData.isPresent()) {
	      Book _book = bookData.get();
	      _book.setTitle(book.getTitle());
	      _book.setAuthor(book.getAuthor());
	      _book.setPrice(book.getPrice());
	      return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/books/{id}")
	  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
	    try {
	      bookRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
