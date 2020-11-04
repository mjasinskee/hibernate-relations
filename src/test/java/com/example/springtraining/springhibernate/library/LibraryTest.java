package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LibraryTest {

    @Autowired BookRepository bookRepository;
    @Autowired AuthorRepository authorRepository;

    @Test
    public void shouldSaveBooksAndAuthros() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");

        Author author1 = new Author("name1", "lastName1");
        Author author2 = new Author("name2", "lastName2");

        Book savedBook1 = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);
        Author savedAuthor1 = authorRepository.save(author1);
        Author savedAuthor2 = authorRepository.save(author2);

        //when

        Optional<Author> byId = authorRepository.findById(savedAuthor1.getId());
        Optional<Book> byId1 = bookRepository.findById(savedBook1.getId());
        if (byId.isPresent() && byId1.isPresent()) {
            Author author = byId.get();
            Book book = byId1.get();
            book.setAuthor(author);
            author.setBook(book1);
            authorRepository.save(author);
        }

        //then
        List<Author> all = authorRepository.findAll();
        System.out.println("result author: " + all);
    }

}
