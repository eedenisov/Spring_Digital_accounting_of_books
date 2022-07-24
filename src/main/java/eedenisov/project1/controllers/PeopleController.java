package eedenisov.project1.controllers;

import eedenisov.project1.dao.PersonDAO;
import eedenisov.project1.models.Person;

import eedenisov.project1.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author eedenisov
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDao;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String listPeople(Model model) {
        model.addAttribute("people",personDao.listPeople());

        return "people/list_people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.showPerson(id));
        model.addAttribute("personWithBooks", personDao.showPersonWithBook(id));

        return "people/show_person";
    }



    @GetMapping("/add")
    public String addPerson(@ModelAttribute("person") Person person) {
        return "people/add_person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/add_person";
        }
        personDao.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.showPerson(id));

        return "people/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit_person";
        }
        personDao.updatePerson(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.deletePerson(id);

        return "redirect:/people";
    }



}
