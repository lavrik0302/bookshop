package controler.FindRequests;

import lombok.Data;

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
    public String toSQLStringStatement() {
        StringBuilder sb=new StringBuilder();
        if (!getPersonIds().isEmpty()) {
            sb.append("person_id ");
            if (getPersonIds().size() > 1) {
                sb.append("in (");
                for (UUID person_id : getPersonIds()) {
                    sb.append("'" + person_id + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getPersonIds().get(0) + "' AND ");
            }
        }
        if (!getPersonNames().isEmpty()) {
            sb.append("name ");
            if (getPersonNames().size() > 1) {
                sb.append("in (");
                for (String name : getPersonNames()) {
                    sb.append("'" + name + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getPersonNames().get(0) + "' AND ");
            }
        }
        if (!getPersonSurnames().isEmpty()) {
            sb.append("surname ");
            if (getPersonSurnames().size() > 1) {
                sb.append("in (");
                for (String surname : getPersonSurnames()) {
                    sb.append("'" + surname + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getPersonSurnames().get(0) + "' AND ");
            }
        }
        if (!getPersonMobilenumbers().isEmpty()) {
            sb.append("mobilenumber ");
            if (getPersonMobilenumbers().size() > 1) {
                sb.append("in (");
                for (String mobilenumber :getPersonMobilenumbers()) {
                    sb.append("'" + mobilenumber + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getPersonMobilenumbers().get(0) + "' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
