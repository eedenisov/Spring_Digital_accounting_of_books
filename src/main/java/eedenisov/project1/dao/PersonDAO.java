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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> listPeople() {
        return jdbcTemplate.query("SELECT * FROM Person",
                //обьект, который отображает наши строки из таблицы в сущности
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> showPerson(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void addPerson(Person addPerson) {
        jdbcTemplate.update("INSERT INTO Person(fullName, birthYear) VALUES ( ?, ?)",
                addPerson.getFullName(), addPerson.getBirthYear());
    }

    public void updatePerson(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET fullName=?, birthYear=? WHERE id=?",
                updatePerson.getFullName(), updatePerson.getBirthYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> showPersonWithBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
