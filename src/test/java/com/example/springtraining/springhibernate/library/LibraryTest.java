package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryTest {

    @Autowired BookRepository bookRepository;
    @Autowired AuthorRepository authorRepository;

    @Test
    public void shouldSAveBooksAndAuthors() {
        //given
        Author author1 = new Author("name1", "lastName1");
        Author author2 = new Author("name2", "lastName2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author2);

        //when
        authorRepository.save(author1);
        authorRepository.save(author2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        //then
        System.out.println("book result: " + bookRepository.findAll());
    }
}
