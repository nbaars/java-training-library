package com.xebia.library.jdbc.books.services;

import com.xebia.library.jdbc.books.domain.Book;
import com.xebia.library.jdbc.books.domain.BookImage;
import com.xebia.library.jdbc.books.repositories.LibraryRepository;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryService {

  private final LibraryRepository libraryRepository;

  public LibraryService(LibraryRepository libraryRepository) {
    this.libraryRepository = libraryRepository;
  }

  /**
   * @RolesAllowed is an annotation that allows you to specify which roles are allowed to invoke the
   * method. See `SecurityConfiguration` for the configuration of the roles.
   */
  @RolesAllowed({"LIBRARIAN", "USER"})
  public List<Book> getAllBooks() {
    return libraryRepository.findAll();
  }

  @RolesAllowed({"LIBRARIAN", "USER"})
  public List<Book> getBooksByTitle(String title) {
    return libraryRepository.findByTitle(title);
  }

  public void addBook(Book book) {
    if (book.price() >= 100) {
      throw new IllegalArgumentException("Book price above 100, nobody will buy it!");
    }
    libraryRepository.saveBook(book);
  }

  @RolesAllowed({"LIBRARIAN", "USER"})
  @Transactional
  public void deleteBook(String title) {
    libraryRepository.deleteByTitle(title);
  }

  @RolesAllowed({"LIBRARIAN", "USER"})
  @Transactional
  public void deleteAllBooks() {
    libraryRepository.deleteAll();
  }

  @RolesAllowed({"LIBRARIAN", "USER"})
  @Transactional
  public void addBookImage(Long id, byte[] image) {
    Objects.requireNonNull(image, "Image cannot be null");

    /**
     * `findById` returns an `Optional` which is a container object which may or may not contain a
     * non-null value.
     */
    Book book = libraryRepository.findById(id).orElseThrow();
    libraryRepository.saveImage(book, BookImage.of(image));
  }
}
