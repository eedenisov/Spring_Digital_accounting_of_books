package eedenisov.project1.util;

import eedenisov.project1.dao.BookDAO;
import eedenisov.project1.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author eedenisov
 */
@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (bookDAO.showBook(book.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "This title is already taken");
        }
    }

}
