package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void shouldPersisBooksAndAuthors() {
        //given
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author1);
        Book book3 = new Book("ISBN3", "title3", author2);

        //when
        authorRepository.save(author1);
        authorRepository.save(author2);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        //then
        List<Book> all = bookRepository.findAll();
        System.out.println("result");
        System.out.println(all);
    }

}
