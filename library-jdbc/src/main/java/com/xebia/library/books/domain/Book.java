package com.xebia.library.books.domain;

import java.util.ArrayList;
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

  public static Book of(String title, Author author, int yearPublished, double price) {
    return new Book(Optional.empty(), title, author, yearPublished, price, new ArrayList<>());
  }

  public static Book of(
      String title, Author author, int yearPublished, double price, List<BookImage> images) {
    return new Book(Optional.empty(), title, author, yearPublished, price, images);
  }
}
