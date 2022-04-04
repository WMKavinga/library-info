package com.hecampus.libraryinfo.repository;

import com.hecampus.libraryinfo.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    public void saveBook() {
        Book book = new Book();

        book.setId("01");
        book.setName("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setIsbn(154);

        assertEquals(book, bookRepository.save(book));
    }

    @Test
    public void getBook() {

        String book = "Harry Potter";
        String expectedBook = bookRepository.findById("01").get().getName();
        assertEquals(expectedBook, book);
    }

}
