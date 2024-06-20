package org.books.api.repositories;

import org.books.api.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author")
    Long countBooksByAuthor(@Param("author") String author);
}
