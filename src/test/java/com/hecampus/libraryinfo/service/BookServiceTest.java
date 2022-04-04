package com.hecampus.libraryinfo.service;

import com.hecampus.libraryinfo.dto.BookDTO;
import com.hecampus.libraryinfo.model.Book;
import com.hecampus.libraryinfo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    public void addUsersTest() {

        Book book = new Book();
        book.setId("01");
        book.setName("Harry Potter");
        book.setAuthor("j.K Rowling");
        book.setIsbn(123);

        BookDTO bookSaved = new BookDTO();
        bookSaved.setId("01");
        bookSaved.setName("Harry Potter");
        bookSaved.setAuthor("j.K Rowling");
        bookSaved.setIsbn(123);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO savedBook = bookService.saveBook(bookSaved);
        assertThat(savedBook.getName()).isNotNull();
    }

    @Test
    public void getBooksTest() {
        List<Book> bookArrayList = new ArrayList<>();
        Book book = new Book();
        book.setId("01");
        book.setName("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setIsbn(156);
        bookArrayList.add(book);

        when(bookRepository.findAll()).thenReturn(bookArrayList);

        List<BookDTO> books = bookService.getBookList();
        assertThat(books.get(0).getName()).isEqualTo("Harry Potter");
    }

    @Test
    public void getUserByIdTest(){
        Book book = new Book();
        book.setId("01");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Book expected = bookService.findBookById(book.getId());

        assertThat(expected).isSameAs(book);
        verify(bookRepository).findById(book.getId());

    }

    @Test
    public void updateBooksTest(){
        Book book = new Book();
        book.setId("01");
        book.setName("Harry Potter");

        BookDTO newBook = new BookDTO();
        book.setName("Harry Potter");

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        bookService.updateBook(book.getId(), newBook);

        verify(bookRepository).findById(book.getId());
    }

    @Test
    public void deleteBooksTest(){
            Book book = new Book();
            book.setId("01");

        bookService.deleteBook(book.getId());
        verify(bookRepository).deleteById(book.getId());

    }



}
