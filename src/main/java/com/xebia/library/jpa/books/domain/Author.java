package com.xebia.library.jpa.books.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.xebia.library.jpa.books.repositories.LibraryRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * @Entity annotation is used to indicate that the class is an entity. Note cannot use records as
 * JPA entities do not support records yet. Only plays a role when using JPA.
 *
 * <p>Note: JPA is the Java Persistence API. It is a specification for accessing, persisting, and
 * managing data between Java objects / classes and a relational database.
 *
 * <p>Note: if you only want to use JDBC, you can remove this class and the {@link
 * LibraryRepository} and use plain records instead.
 */
@Entity
/**
 * @Table annotation is used to specify the primary table for the annotated entity. Only plays a
 * role when using JPA.
 */
@Table(name = "AUTHORS")
public class Author {
  /**
   * @Id annotation is used to specify the primary key of an entity
   */
  @Id
  /**
   * @GeneratedValue annotation is used to specify the primary key generation strategy to use. Only
   * plays a role when using JPA.
   */
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int age;

  protected Author() {}

  @JsonCreator
  public Author(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Author) obj;
    return Objects.equals(this.name, that.name) && this.age == that.age;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, age);
  }

  @Override
  public String toString() {
    return "Author[" + "name=" + name + ", " + "age=" + age + ']';
  }
}
