package com.example.springtraining.springhibernate.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void shouldChangeName() {
        //given
        Book book1 = bookRepository.save(new Book("ISBN1", "title1"));
        Author author1 = authorRepository.save(new Author("name1", "lastName1"));
        Author author2 = authorRepository.save(new Author("name2", "lastName2"));

        book1.setAuthors(Stream.of(author1, author2).collect(Collectors.toSet()));
        Book save = bookRepository.save(book1);

        //when

        Book book = bookRepository.findById(save.getId()).get();
        Author authorByName = book.authors.stream().filter(author -> author.getFirstName().equals("name1")).findFirst().get();
        authorByName.setFirstName("other name");
        authorByName.setLastName("other lastname");
        bookRepository.saveAndFlush(book);

        //then
        assertThat(authorRepository.findAuthorByFirstNameAndLastName("other name", "other lastname").isPresent()).isTrue();
    }

    @Test
    public void shouldNotRemoveAuthorWithoutBooks() {
        //given
        Book book = new Book("isbn1", "title1");
        book.addAuthor(new Author("firstName1", "lastName1"));
        book.addAuthor(new Author("firstName2", "lastName2"));
        Book save = bookRepository.save(book);
        List<Author> all = authorRepository.findAll();

        //when
        Book book1 = bookRepository.findById(save.getId()).get();
        Author firstName1 = book1.authors.stream().filter(author -> author.getFirstName().equals("firstName1")).findFirst().get();
        book1.removeAuthor(firstName1);
        bookRepository.save(book1);

        //then
        assertThat(authorRepository.findAll().size()).isEqualTo(2);
        assertThat(bookRepository.findAll().size()).isEqualTo(1);
        assertThat(bookRepository.findById(save.getId()).get().authors.size()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveAuthor() {
        //given
        Book book = new Book("isbn1", "title1");
        book.addAuthor(new Author("firstName1", "lastName1"));
        book.addAuthor(new Author("firstName2", "lastName2"));
        Book save = bookRepository.save(book);

        //when
        Book book1 = bookRepository.findById(save.getId()).get();
        Author firstName1 = book1.authors.stream().filter(author -> author.getFirstName().equals("firstName1")).findFirst().get();
        book1.removeAuthor(firstName1);
        bookRepository.saveAndFlush(book1);
        authorRepository.delete(firstName1);

        //then
        System.out.println("all authors: " + authorRepository.findAll());
        System.out.println("all books: " + bookRepository.findAll());
        assertThat(authorRepository.findAll().size()).isEqualTo(1);
        assertThat(bookRepository.findAll().size()).isEqualTo(1);
    }

}
