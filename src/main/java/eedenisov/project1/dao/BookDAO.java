package eedenisov.project1.dao;

import eedenisov.project1.models.Book;
import eedenisov.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author eedenisov
 */
@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> listBooks() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> showBook(String title) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title=?", new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book showBook(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE bookId=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void addBook(Book addBook) {
        jdbcTemplate.update("INSERT INTO Book(title, author) VALUES (?, ?)",
                addBook.getTitle(), addBook.getAuthor());
    }

    public void updateBook(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=? WHERE bookId=?",
                updateBook.getTitle(), updateBook.getAuthor(), id);
    }

    public void deleteBook(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE bookId=?",
                bookId);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT * FROM Book JOIN Person ON Book.id=Person.id"
                        + " WHERE Book.bookId=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE Book SET id=NULL WHERE bookId=?", id);
    }

    public void assignBook(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET id=? WHERE bookId=?",
                person.getId(),id);
    }


}
