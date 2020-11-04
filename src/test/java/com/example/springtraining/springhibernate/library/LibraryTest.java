package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class LibraryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void shouldStoreBooksAndAuthors() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");
        Book book3 = new Book("ISBN3", "title3");
        Author author1 = new Author("name1", "lastName1", Arrays.asList(book1, book2));
        Author author2 = new Author("name1", "lastName1", Arrays.asList(book3));

        //when
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        authorRepository.save(author1);
        authorRepository.save(author2);

        //then
        List<Author> all = authorRepository.findAll();
        System.out.println("result: " + all);
    }

}
