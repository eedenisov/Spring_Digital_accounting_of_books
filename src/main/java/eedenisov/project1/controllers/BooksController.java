package eedenisov.project1.controllers;

import eedenisov.project1.dao.BookDAO;
import eedenisov.project1.dao.PersonDAO;
import eedenisov.project1.models.Book;

import eedenisov.project1.models.Person;
import eedenisov.project1.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author eedenisov
 */
@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;

    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books",bookDAO.listBooks());

        return "books/list_books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.showBook(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("bookOwner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.listPeople());
        }

        return "books/show_book";
    }

    @GetMapping("/add_book")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/add_book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/add_book";
        }
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit_book")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.showBook(id));

        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit_book";
        }
        bookDAO.updateBook(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);

        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id,
                             @ModelAttribute("person") Person person) {
        bookDAO.assignBook(id, person);

        return "redirect:/books/" + id;
    }
}
