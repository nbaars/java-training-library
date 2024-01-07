package com.xebia.library.jdbc.books.domain;

import java.util.List;
import java.util.Optional;

public record Book(
    Optional<Long> id,
    String title,
    Author author,
    int yearPublished,
    double price,
    List<BookImage> bookImages) {

  public void addImage(BookImage bookImage) {
    bookImages.add(bookImage);
  }

  public void addImages(List<BookImage> images) {
    bookImages.addAll(images);
  }

  public static Book of(
      String title, Author author, int yearPublished, double price, List<BookImage> bookImages) {
    return new Book(Optional.empty(), title, author, yearPublished, price, bookImages);
  }
}
