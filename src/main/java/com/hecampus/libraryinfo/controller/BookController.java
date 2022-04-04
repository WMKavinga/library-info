package com.hecampus.libraryinfo.controller;

import com.hecampus.libraryinfo.dto.BookDTO;
import com.hecampus.libraryinfo.model.Book;
import com.hecampus.libraryinfo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBookList() {
        return new ResponseEntity<>(service.getBookList(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") String id){

            Book book = service.findBookById(id);
            return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO book){

        BookDTO newBook = service.saveBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);

    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@RequestBody BookDTO book,@PathVariable("id") String id){
        service.updateBook(id,book);
        return new ResponseEntity<>("Book Details Updated Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {

            service.deleteBook(id);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }


}
