package com.example.springtraining.springhibernate.library;

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
    public void shouldSaveBooksAndAuthros() {
        //given
        Book book1 = new Book("ISBN1", "title1");

        Author author1 = new Author("name1", "lastName1");
        author1.setBook(book1);

        //when
        Author savedAuthor1 = authorRepository.save(author1);

        //then
        Optional<Author> author = authorRepository.findAuthorByFirstNameAndLastName("name1", "lastName1");
        assertThat(author.get().getBook().getTitle()).isEqualToIgnoringCase("title1");
    }

    @Test
    public void shouldRemoveOrphants() {
        //given
        Book book1 = new Book("ISBN1", "title1");

        Author author1 = new Author("name1", "lastName1");
        author1.setBook(book1);

        //when
        Author savedAuthor1 = authorRepository.save(author1);
        //and
        author1.removeBook();

        //then
        List<Book> all = bookRepository.findAll();
        assertThat(all).isEmpty();
    }

}
