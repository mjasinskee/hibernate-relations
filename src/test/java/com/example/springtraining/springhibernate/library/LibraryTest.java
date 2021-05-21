package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LibraryTest {

    @Autowired AuthorRepository authorRepository;
    @Autowired BookRepository bookRepository;

    @Test
    public void shouldStoreBooksAndAuthors() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");
        Book book3 = new Book("ISBN3", "title3");
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book3);

        //when
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);

        //then
        List<Author> authors = authorRepository.findAll();
        assertThat(authors.size()).isEqualTo(2);
        assertThat(authors).containsExactlyInAnyOrder(author1, author2);

        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(3);
        assertThat(books).containsExactlyInAnyOrder(book1, book2, book3);

        System.out.println("author result: " + authorRepository.findAll());
        System.out.println("book result: " + bookRepository.findAll());
    }

    @Test
    public void shouldUpdateBookTitleWhenUpdatingAuthor() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");
        Book book3 = new Book("ISBN3", "title3");
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book3);

        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);

        //when
        Author author = authorRepository.findAuthorByFirstNameAndLastName("name1", "lastname1").get();
        Book book = author.getBooks().get(0);
        book.setTitle("new title");
        authorRepository.saveAndFlush(author);

        //then
        Optional<Book> foundBook = bookRepository.findBookByTitle("new title");
        assertThat(foundBook.isPresent()).isTrue();
    }

}
