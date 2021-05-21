package com.example.springtraining.springhibernate.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

//    @Query("select b  from Book b where b.author = :author")
    List<Book> findBookByAuthor(Author author);

    Optional<Book> findBookByTitle(String title);
}
