package ru.slimercorp.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.slimercorp.springcourse.dao.PersonDAO;
import ru.slimercorp.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

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

        List<Person> list = personDAO.index();
        boolean exist = false;
        String name = person.getName();
        for (Person ps : list) {
            if (ps.getName().equals(name)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            errors.rejectValue("name", "", "This person is already exist");
        }
    }
}
