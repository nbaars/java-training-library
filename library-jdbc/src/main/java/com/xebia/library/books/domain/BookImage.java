package com.xebia.library.books.domain;

import java.util.Optional;

public record BookImage(Optional<Long> bookId, byte[] image) {

  public static BookImage of(byte[] image) {
    return new BookImage(Optional.empty(), image);
  }
}
