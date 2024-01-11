package com.xebia.library;

import com.xebia.library.books.domain.Author;
import com.xebia.library.books.domain.Book;
import com.xebia.library.books.repositories.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
@Transactional
class LibraryRepositoryTest {
  @Autowired private LibraryRepository libraryRepository;

  @Container
  private static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:11.1")
          .withDatabaseName("integration-tests-db")
          .withUsername("username")
          .withPassword("password");

  static {
    postgreSQLContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @Test
  void shouldSaveBook() {
    Author author = new Author("John Doe", 23);
    Book book = new Book("Java 21", author, 2022, 12.25);
    libraryRepository.save(book);
  }

  @Test
  void getBooks() {}
}
