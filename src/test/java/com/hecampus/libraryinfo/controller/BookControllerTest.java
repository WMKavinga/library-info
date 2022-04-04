package com.hecampus.libraryinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hecampus.libraryinfo.dto.BookDTO;
import com.hecampus.libraryinfo.model.Book;
import com.hecampus.libraryinfo.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGetExample() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        BookDTO book = new BookDTO();
        book.setId("1");
        book.setName("Arun");
        books.add(book);
        when(bookService.getBookList()).thenReturn(books);
        mockMvc.perform(get("/api/books")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Arun")));
    }

    @Test
    public void testGetUserByIdExample() throws Exception {
        Book book = new Book();
        book.setId("01");
        book.setName("Harry Potter");

        given(bookService.findBookById(book.getId())).willReturn(book);

        mockMvc.perform(get("/api/books/{id}",book.getId())
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("name", is(book.getName())));
    }

    @Test
    public void testPostExample() throws Exception {
        BookDTO book = new BookDTO();
        book.setId("1");
        book.setName("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn(156);
        when(bookService.saveBook(ArgumentMatchers.any())).thenReturn(book);
        String json = mapper.writeValueAsString(book);
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo("1")))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Harry Potter")))
                .andExpect(jsonPath("$.author",Matchers.equalTo("J.K. Rowling")))
                .andExpect(jsonPath("$.isbn" , Matchers.equalTo(156)));
    }

    @Test
    public void updateUserByIdTest() throws Exception {
        BookDTO book = new BookDTO();
        book.setName("Harry Potter");
        book.setId("156");

        Book book1 = new Book();
        book.setName("Harry Potter");
        book.setId("156");

        given(bookService.updateBook(book.getId(), book)).willReturn(book1);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/api/books/{id}",book.getId())
            .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExample() throws Exception {
        BookDTO book = new BookDTO();
        book.setId("01A");
        book.setName("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn(156);


        doNothing().when(bookService).deleteBook(book.getId());

        mockMvc.perform(delete("/api/books/{id}",book.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
}
