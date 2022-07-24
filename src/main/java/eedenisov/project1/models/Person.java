package eedenisov.project1.models;

import javax.validation.constraints.*;

/**
 * @author eedenisov
 */

public class Person {

    private int id;
    @NotEmpty(message = "Full name should not be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Full name should be in this format: "
            + "Petrov Petr Petrovich")
    private String fullName;
    @Min(value = 1921, message = "Year of birth should be greater than 1921")
    @Max(value = 2022, message = "Year of birth should be less than 2022")
    @NotNull(message = "Year of birth should not be empty")
    private int birthYear;

    public Person() {

    }

    public Person(int id, String fullName, int birthYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
