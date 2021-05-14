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
    public void shouldSaveBooksAndAuthors() {
        //given
        Author author1 = new Author("name1", "lastName1");
        Author author2 = new Author("name2", "lastName2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author2);

        //when
//        authorRepository.save(author1);
//        authorRepository.save(author2);
        bookRepository.save(book1);
        bookRepository.save(book2);

        //then
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(2);
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
        Optional<Book> book = bookRepository.findBookByTitle("title1");
        book.ifPresent(b -> {
            b.getAuthor().setFirstName("AnotherName");
            bookRepository.save(b);
        });

        //then
        Optional<Book> saved = bookRepository.findBookByTitle("title1");
        assertThat(saved.get().getAuthor().getFirstName()).isEqualToIgnoringCase("anothername");
        //and
        Optional<Author> author = authorRepository.findAuthorByFirstNameAndLastName("AnotherName", "lastName1");
        assertThat(author.get().getFirstName()).isEqualToIgnoringCase("anothername");
    }
}
