package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<Book> books = Arrays.asList(book1, book2, book3);

        bookRepository.saveAndFlush(book1);
        bookRepository.saveAndFlush(book2);
        bookRepository.saveAndFlush(book3);
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);

        //when
        List<Author> all = authorRepository.findAll();
        for (int i = 0, allSize = all.size(); i < allSize; i++) {
            Author author = all.get(i);
            Book book = books.get(i);
            book.setAuthor(author);
            author.getBooks().add(book);
//            bookRepository.saveAndFlush(book);
//            authorRepository.saveAndFlush(author);
        }

        //then
        System.out.println("author result: " + authorRepository.findAll());
        System.out.println("book result: " + bookRepository.findAll());
    }

}
