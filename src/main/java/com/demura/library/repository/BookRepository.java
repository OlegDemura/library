package com.demura.library.repository;

import com.demura.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("update Book b set b.person=null where b.id=:id")
    void free(@Param("id") int id);

    Optional<Book> findByTitleAndAuthor(String title, String author);
}
