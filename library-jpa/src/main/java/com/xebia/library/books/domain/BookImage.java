package com.xebia.library.books.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import java.io.Serializable;

/**
 * @Embeddable annotation is used to indicate that the class can be embedded in another entity
 */
@Embeddable
public class BookImage implements Serializable {
  /**
   * @Lob annotation is used to indicate that the property or field should be persisted as a large
   * object to a database-supported large object type.
   */
  @Lob private byte[] image;

  /** The default constructor is required by JPA */
  protected BookImage() {}

  public BookImage(byte[] image) {
    this.image = image;
  }

  public byte[] getImage() {
    return image;
  }
}
