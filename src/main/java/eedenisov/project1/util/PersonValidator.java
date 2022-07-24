package eedenisov.project1.util;

import eedenisov.project1.dao.PersonDAO;
import eedenisov.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author eedenisov
 */
@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.showPerson(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "This full name is already taken");
        }

    }
}
