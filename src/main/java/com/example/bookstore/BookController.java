package com.example.bookstore;

import com.example.bookstore.Book;
import com.example.bookstore.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class BookController {

    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    @GetMapping("/books/")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return bookRepository.findById(id).get();
    }

    @PostMapping("/books/")
    public HttpStatus addBook(@RequestBody Book book){
        bookRepository.save(book);

        return HttpStatus.CREATED;
    }

    @PutMapping("/books/{id}")
    public HttpStatus updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            bookRepository.save(book);
            return HttpStatus.OK;
        }).orElse(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/books/{id}")
    public HttpStatus deleteBook(@PathVariable("id") Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}