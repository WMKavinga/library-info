package com.hecampus.libraryinfo.service;

import com.hecampus.libraryinfo.dto.BookDTO;
import com.hecampus.libraryinfo.model.Book;
import com.hecampus.libraryinfo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public List<BookDTO> getBookList(){
        return repository.findAll().stream()
                .map(
                        book -> {
                            BookDTO dto = new BookDTO();
                            dto.setName(book.getName());
                            dto.setAuthor(book.getAuthor());
                            dto.setIsbn(book.getIsbn());
                            return dto;
                        })
                .collect(Collectors.toList());
    }

    public BookDTO saveBook(BookDTO book) {
        Book newBook = new Book();
        newBook.setName(book.getName());
        newBook.setAuthor(book.getAuthor());
        newBook.setIsbn(book.getIsbn());
        repository.save(newBook);
        return book;
    }

    public Book findBookById(String id){
        Optional<Book> book = repository.findById(id);
        if(book.isPresent()){
            return book.get();
        }
        else
            return null;

    }

    public Book updateBook(String id, BookDTO bookDTO){

        Optional<Book> book = repository.findById(id);
        if (book.isPresent()){
            Book book1 = book.get();

            book1.setName(bookDTO.getName());
            book1.setIsbn(bookDTO.getIsbn());
            book1.setAuthor(bookDTO.getAuthor());

           return repository.save(book1);
        } else{
            return null;
        }

    }

    public void deleteBook(String id) {
        repository.deleteById(id);
    }


}
