package com.xebia.library.jdbc.books.domain;

import java.util.Optional;

public record Author(Optional<Long> id, String name, Integer age) {

  public static Author of(String name, Integer age) {
    return new Author(Optional.empty(), name, age);
  }
}
