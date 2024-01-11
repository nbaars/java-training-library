package com.xebia.library.books.repositories;

import static java.util.stream.Collectors.groupingBy;

import com.xebia.library.books.domain.Author;
import com.xebia.library.books.domain.Book;
import com.xebia.library.books.domain.BookImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * @Repository annotation is used to indicate that the class provides the mechanism for storage
 *
 * <p>You can also opt for a AuthorRepository to save and load the authors.
 */
@Repository
public class LibraryRepository {

  private final JdbcTemplate jdbcTemplate;
  private RowMapper<Book> bookRowMapper =
      (rs, rowNum) -> {
        var author =
            new Author(
                Optional.of(rs.getLong("author_id")), rs.getString("name"), rs.getInt("age"));
        return new Book(
            Optional.of(rs.getLong("id")),
            rs.getString("title"),
            author,
            rs.getInt("year_published"),
            rs.getDouble("price"),
            new ArrayList<>());
      };
  private RowMapper<BookImage> bookImageRowMapper =
      (rs, rowNum) -> new BookImage(Optional.of(rs.getLong("book_id")), rs.getBytes("image"));

  public LibraryRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> findAll() {
    List<Book> books =
        jdbcTemplate.query(
            "SELECT * FROM books join authors on books.author_id = authors.id ", bookRowMapper);
    Map<Long, List<BookImage>> bookImagesPerBook =
        jdbcTemplate
            .query(
                "SELECT * FROM book_images join books on book_images.book_id = books.id",
                bookImageRowMapper)
            .stream()
            .collect(groupingBy(bookImage -> bookImage.bookId().orElseThrow()));
    books.forEach(book -> book.addImages(bookImagesPerBook.getOrDefault(book.id(), List.of())));

    return books;
  }

  public List<Book> findByTitle(String title) {
    return jdbcTemplate.query(
        "SELECT * from books join authors on books.author_id = authors.id where title = ?",
        bookRowMapper,
        title);
  }

  public void saveBook(Book book) {
    var author = book.author();
    var authorInsert =
        new SimpleJdbcInsert(jdbcTemplate)
            .withCatalogName("library")
            .usingColumns("name", "age")
            .withTableName("authors")
            .usingGeneratedKeyColumns("id");
    var authorId =
        authorInsert.executeAndReturnKey(Map.of("name", author.name(), "age", author.age()));
    jdbcTemplate.update(
        "INSERT INTO books(title, year_published, price, author_id) VALUES (?, ?, ?, ?)",
        book.title(),
        book.yearPublished(),
        book.price(),
        authorId);
  }

  public void deleteByTitle(String title) {
    jdbcTemplate.update("DELETE FROM books WHERE title = ?", title);
  }

  public void deleteAll() {
    jdbcTemplate.update("DELETE FROM books");
  }

  public Optional<Book> findById(Long id) {
    return jdbcTemplate
        .query(
            "SELECT * FROM books join authors on books.author_id = authors.id where books.id = ?",
            bookRowMapper,
            id)
        .stream()
        .findFirst();
  }

  public void saveImage(Book book, BookImage bookImage) {
    jdbcTemplate.update(
        "INSERT INTO book_images(book_id, image) VALUES (?, ?)", book.id(), bookImage.image());
  }
}
