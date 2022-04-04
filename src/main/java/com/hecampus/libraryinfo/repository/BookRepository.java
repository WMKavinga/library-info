package com.hecampus.libraryinfo.repository;


import com.hecampus.libraryinfo.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface BookRepository extends MongoRepository <Book,String> {

}
