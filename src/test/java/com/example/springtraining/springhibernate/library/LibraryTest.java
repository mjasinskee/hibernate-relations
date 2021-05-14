package com.example.springtraining.springhibernate.library;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LibraryTest {


    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

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
        //setting in test
//        book1.addAuthor(author1);
//        author1.addBook(book1);
//        book1.addAuthor(author2);
//        author2.addBook(book1);
//        book2.addAuthor(author3);
//        author3.addBook(book2);
//        book2.addAuthor(author4);
//        author4.addBook(book2);

//        book1.setAuthors(Stream.of(author1, author2).collect(Collectors.toSet()));
//        book2.setAuthors(Stream.of(author3, author4).collect(Collectors.toSet()));

        book1.authors.add(author1);
        author1.addBook(book1);
        book1.authors.add(author2);
        author2.addBook(book1);
        book2.authors.add(author3);
        author3.addBook(book2);
        book2.authors.add(author4);
        author4.addBook(book2);

        bookRepository.saveAndFlush(book1);
        bookRepository.saveAndFlush(book2);

        List<Book> allBooks = bookRepository.findAll();
        List<Author> allAuthors = authorRepository.findAll();
        System.out.println("result: " + allBooks);
        System.out.println("authors: " + allAuthors);

        //then
        Optional<Book> savedBook = bookRepository.findById(book1.getId());
        assertThat(savedBook.get().authors.size()).isEqualTo(2);

        Optional<Author> savedAuthor = authorRepository.findById(author1.getId());
        assertThat(savedAuthor.get().books.size()).isEqualTo(1);
//
//        assertThat(allBooks).isNotEmpty();
//        assertThat(allAuthors).isNotEmpty();
    }

    @Test
    public void shouldRemoveBookFromAuthorButNotFromDB() {
        //given
        Book book1 = bookRepository.save(new Book("ISBN1", "title1"));
        Book book2 = bookRepository.save(new Book("ISBN2", "title2"));
        Author author1 = authorRepository.save(new Author("name1", "lastName1"));
        Author author2 = authorRepository.save(new Author("name2", "lastName2"));
        Author author3 = authorRepository.save(new Author("name3", "lastName3"));
        Author author4 = authorRepository.save(new Author("name4", "lastName4"));

        book1.setAuthors(Stream.of(author1, author2).collect(Collectors.toSet()));
        book2.setAuthors(Stream.of(author3, author4).collect(Collectors.toSet()));
        bookRepository.save(book1);
        bookRepository.save(book2);

        //when
        Optional<Book> book = bookRepository.findById(book1.getId());
        book.ifPresent(b -> {
            Author authorToRemove = b.authors.stream().filter(author -> author.getFirstName().equals("name1")).findFirst().get();
            b.authors.remove(authorToRemove);
            bookRepository.save(b);
        });

        //then
        Optional<Book> savedBook = bookRepository.findById(book1.getId());
        assertThat(savedBook.get().authors.size()).isEqualTo(1);
        assertThat(authorRepository.findAll().size()).isEqualTo(4);
    }
}
