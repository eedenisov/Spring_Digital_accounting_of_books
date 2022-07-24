package eedenisov.project1.models;

import javax.validation.constraints.*;


/**
 * @author eedenisov
 */
public class Book {

    private int bookId;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, message = "Size should be minimum 2 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author should be in this format: "
            + "Petr Petrov")
    private String author;


    public Book() {

    }

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
