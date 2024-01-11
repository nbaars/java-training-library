package com.xebia.library.books.controllers;

import com.xebia.library.books.domain.Book;
import com.xebia.library.books.services.LibraryService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The `@RestController` annotation is a convenience annotation that combines `@Controller` and
 * `@ResponseBody`.
 */
@RestController
/**
 * The `@RequestMapping` annotation is used to map web requests onto specific handler classes and/or
 * handler methods. In this case, we map all requests starting with `/library` to this controller.
 */
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * Constructor, the `libraryService` will be wired automatically. Using `@Autowired` before the
     * parameter is not necessary as Spring will detect it is the only constructor which makes
     * `Autowired` redundant.
     */
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * @RequestParam specifies the `query parameters` of the HTTP request. By default, the query param
     * is required. With `Optional` we implicitly say it can be optional.
     */
    @GetMapping("/books")
    public List<Book> books(@RequestParam Optional<String> title) {
        // We use method references instead of a plain lambda.
        return title.map(libraryService::getBooksByTitle).orElseGet(libraryService::getAllBooks);
    }

    /**
     * @RequestBody indicates that we expect a body which has to be converted to a `Book` instance.
     */
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        return book;
    }

    @DeleteMapping("/books")
    public void deleteBook(@RequestParam Optional<String> title) {
        title.ifPresentOrElse(libraryService::deleteBook, () -> libraryService.deleteAllBooks());
    }

    /**
     * The path variable is mapped to the `id` parameter. The `@PathVariable` annotation is used to
     * bind a path variable to a method parameter. If the name of the method parameter matches the
     * name of the path variable exactly, this can be omitted.
     */
    @PostMapping("/books/{id}/images")
    public void addBookImage(@RequestParam("image") MultipartFile bookImage, @PathVariable Long id)
            throws IOException {
        libraryService.addBookImage(id, bookImage.getBytes());
    }
}
