package com.xebia.library.jpa.books.repositories;

import com.xebia.library.jpa.books.domain.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository annotation is used to indicate that the class provides the mechanism for storage
 */
@Repository
public interface LibraryRepository extends JpaRepository<Book, Long> {
  List<Book> findByTitle(String title);

  void deleteByTitle(String name);
}
