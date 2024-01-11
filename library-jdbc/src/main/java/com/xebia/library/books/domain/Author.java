package com.xebia.library.books.domain;

import java.util.Optional;

public record Author(Optional<Long> id, String name, Integer age) {

  public static Author of(String name, Integer age) {
    return new Author(Optional.empty(), name, age);
  }
}
