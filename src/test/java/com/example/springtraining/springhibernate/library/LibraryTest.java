package com.example.springtraining.springhibernate.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
        List<Book> books = bookRepository.findAll();
        System.out.println("book result: " + books);
        assertThat(books.size()).isEqualTo(2);
//        books.forEach(book -> assertThat());
//        assertThat()
        System.out.println("");
        System.out.println("dupa: " + authorRepository.findAll());
    }

    @Test
    public void shouldUpdateAuthor() {
        //given
        Author author1 = new Author("name1", "lastName1");
        Author author2 = new Author("name2", "lastName2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        //when
        Optional<Book> book = bookRepository.findById(8L);
        book.ifPresent(b -> {
            b.getAuthor().setFirstName("AnotherName");
            bookRepository.save(b);
        });

        //then
        Optional<Book> saved = bookRepository.findById(8L);
        assertThat(saved.get().getAuthor().getFirstName()).isEqualToIgnoringCase("anothername");
    }
}
