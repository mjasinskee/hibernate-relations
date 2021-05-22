package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        Author author1 = new Author("name1", "lastName1", new HashSet<>(Arrays.asList(book1, book2)));
        Author author2 = new Author("name1", "lastName1", new HashSet<>(Arrays.asList(book3)));

        //when
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//        bookRepository.save(book3);
        authorRepository.saveAndFlush(author1);
        authorRepository.saveAndFlush(author2);

        //then
        List<Author> allAuthors = authorRepository.findAll();
        List<Book> allBooks = bookRepository.findAll();

        assertThat(allAuthors.size()).isEqualTo(2);
        assertThat(allAuthors).containsExactlyInAnyOrder(author1, author2);

        assertThat(allBooks.size()).isEqualTo(3);
        assertThat(allBooks).containsExactlyInAnyOrder(book1, book2, book3);
    }

    @Test
    public void shouldChangeTitleOfBook() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");
        Author author1 = new Author("name1", "lastName1", new HashSet<>(Arrays.asList(book1, book2)));
        Author author = authorRepository.saveAndFlush(author1);

        //when
        Author byId = authorRepository.findById(author.getId()).get();
        Book book = byId.getBooks().stream().filter(b -> b.getTitle().equals("title1")).findFirst().get();
        book.setTitle("new title");
        authorRepository.save(byId);

        //then
        assertThat(bookRepository.findBookByTitle("new title").isPresent()).isTrue();
    }

    @Test
    public void shouldNotRemoveFromDB() {
        //given
        Book book1 = new Book("ISBN1", "title1");
        Book book2 = new Book("ISBN2", "title2");
        Author author1 = new Author("name1", "lastName1", new HashSet<>(Arrays.asList(book1, book2)));
        Author author = authorRepository.saveAndFlush(author1);

        //when
        author.removeBook(book2);

        Author save = authorRepository.save(author);

        //then
        assertThat(save.getBooks().size()).isEqualTo(1);
        assertThat(bookRepository.findById(book2.getId()).isPresent()).isFalse();
    }

}
