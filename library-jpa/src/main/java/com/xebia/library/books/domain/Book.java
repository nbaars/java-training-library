package com.xebia.library.books.domain;

import static jakarta.persistence.FetchType.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "BOOKS")
public class Book implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToOne(
      fetch = EAGER,
      cascade = {CascadeType.PERSIST})
  private Author author;

  private int yearPublished;
  private double price;

  @ElementCollection(fetch = EAGER)
  private List<BookImage> images;

  protected Book() {}

  @JsonCreator
  public Book(String title, Author author, int yearPublished, double price) {
    this.title = title;
    this.author = author;
    this.yearPublished = yearPublished;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Author getAuthor() {
    return author;
  }

  public int getYearPublished() {
    return yearPublished;
  }

  public double getPrice() {
    return price;
  }

  public void addImage(BookImage image) {
    images.add(image);
  }

  public List<BookImage> getBookImages() {
    return this.images;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Book) obj;
    return Objects.equals(this.title, that.title)
        && Objects.equals(this.author, that.author)
        && this.yearPublished == that.yearPublished
        && Double.doubleToLongBits(this.price) == Double.doubleToLongBits(that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, author, yearPublished, price);
  }

  @Override
  public String toString() {
    return "Book["
        + "title="
        + title
        + ", "
        + "author="
        + author
        + ", "
        + "yearPublished="
        + yearPublished
        + ", "
        + "price="
        + price
        + ']';
  }
}
