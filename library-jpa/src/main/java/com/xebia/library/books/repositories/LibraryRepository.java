package com.xebia.library.books.repositories;

import com.xebia.library.books.domain.Book;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository annotation is used to indicate that the class provides the mechanism for storage
 */
@Repository
public interface LibraryRepository extends JpaRepository<Book, Long> {

  List<Book> findByTitle(String title);

  Book save(Book book);

  @CacheEvict(cacheNames = "books", key = "#id", beforeInvocation = true)
  void deleteByTitle(String name);
}
