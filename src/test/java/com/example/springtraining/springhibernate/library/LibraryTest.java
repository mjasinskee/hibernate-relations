package com.example.springtraining.springhibernate.library;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LibraryTest {


    @Autowired BookRepository bookRepository;
    @Autowired AuthorRepository authorRepository;

    @Test
    public void shouldPersist() {
        //given
        Book book1 = bookRepository.save(new Book("ISBN1", "title1"));
        Book book2 = bookRepository.save(new Book("ISBN2", "title2"));
        Author author1 = authorRepository.save(new Author("name1", "lastName1"));
        Author author2 = authorRepository.save(new Author("name2", "lastName2"));
        Author author3 = authorRepository.save(new Author("name3", "lastName3"));
        Author author4 = authorRepository.save(new Author("name4", "lastName4"));

        //when
        book1.setAuthors(Stream.of(author1, author2).collect(Collectors.toSet()));
        book2.setAuthors(Stream.of(author3, author4).collect(Collectors.toSet()));
        bookRepository.save(book1);
        bookRepository.save(book2);

        //then
        List<Book> allBooks = bookRepository.findAll();
        List<Author> allAuthors = authorRepository.findAll();
        System.out.println("result: " + allBooks);
        System.out.println("authors: " + allAuthors);

        assertThat(allBooks).isNotEmpty();
        assertThat(allAuthors).isNotEmpty();
    }
}
