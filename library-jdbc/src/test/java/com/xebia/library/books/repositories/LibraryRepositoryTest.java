package com.xebia.library.books.repositories;

import com.xebia.library.books.domain.Author;
import com.xebia.library.books.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
/** See `application-test.yaml` for the configuration of the test profile */
@ActiveProfiles("test")
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
  @Transactional
  void shouldSaveBook() {
    Author author = Author.of("John Doe", 23);
    Book book = Book.of("Java 21", author, 2022, 12.25);
    libraryRepository.saveBook(book);

    Assertions.assertThat(libraryRepository.findAll()).hasSize(1);
  }
}
