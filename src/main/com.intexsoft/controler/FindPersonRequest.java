package controler;

import lombok.Data;
import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindPersonRequest {

    private List<UUID> personIds = new ArrayList<>();
    private List<String> personNames = new ArrayList<>();
    private List<String> personSurnames = new ArrayList<>();
    private List<String> personMobilenumbers = new ArrayList<>();


    public FindPersonRequest setPersonId(UUID person_id) {
        getPersonIds().add(person_id);
        return this;
    }

    public FindPersonRequest setPersonName(String name) {
        getPersonNames().add(name);
        return this;
    }

    public FindPersonRequest setPersonSurname(String surname) {
        getPersonSurnames().add(surname);
        return this;
    }

    public FindPersonRequest setPersonMobileNumber(String mobilenumber) {
        getPersonMobilenumbers().add(mobilenumber);
        return this;
    }

    public FindPersonRequest setPersonId(List <UUID> person_ids) {
        getPersonIds().addAll(person_ids);
        return this;
    }

    public FindPersonRequest setPersonName(List<String> person_names) {
        getPersonNames().addAll(person_names);
        return this;
    }

    public FindPersonRequest setPersonSurname(List<String> person_surnames) {
        getPersonSurnames().addAll(person_surnames);
        return this;
    }

    public FindPersonRequest setPersonMobileNumber(List<String> person_mobilenumbers) {
        getPersonMobilenumbers().addAll(person_mobilenumbers);
        return this;
    }
}
