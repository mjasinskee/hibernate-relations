package com.example.springtraining.springhibernate.library;

import org.assertj.core.api.Assertions;
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

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void shouldPersisBooksAndAuthors() {
        //given
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author1);
        Book book3 = new Book("ISBN3", "title3", author2);

        //when
//        authorRepository.save(author1);
//        authorRepository.save(author2);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        //then
        List<Book> all = bookRepository.findAll();
        System.out.println("result");
        System.out.println(all);
    }

    @Test
    public void shouldModifyAuthor() {
        //given
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author1);
        Book book3 = new Book("ISBN3", "title3", author2);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        //when
        Optional<Book> book = bookRepository.findById(6L);
        book.ifPresent(b -> {
            b.getAuthor().setFirstName("anotherName");
            bookRepository.save(b);
        });

        //then
        Optional<Book> saved = bookRepository.findById(6L);
        assertThat(saved.isPresent()).isTrue();
        assertThat(saved.get().getAuthor().getFirstName().equalsIgnoreCase("anothername"));
    }

    @Test
    public void shouldFindBooksByAuthor() {
        //given
        Author author1 = new Author("name1", "lastname1");
        Author author2 = new Author("name2", "lastname2");
        Book book1 = new Book("ISBN1", "title1", author1);
        Book book2 = new Book("ISBN2", "title2", author1);
        Book book3 = new Book("ISBN3", "title3", author2);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        Author author = authorRepository.findAll().get(0);

        //when
        List<Book> bookByAuthor = bookRepository.findBookByAuthor(author);

        //then
        assertThat(bookByAuthor.size()).isEqualTo(2);
    }

}
