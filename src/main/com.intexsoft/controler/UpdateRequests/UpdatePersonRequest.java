package controler.UpdateRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdatePersonRequest {
    private UUID personIds;
    private String personNames;
    private String personSurnames;
    private String personMobilenumbers;

    public UpdatePersonRequest setPersonId(UUID person_id) {
        setPersonIds(person_id);
        return this;
    }

    public UpdatePersonRequest setPersonName(String name) {
        setPersonNames(name);
        return this;
    }

    public UpdatePersonRequest setPersonSurname(String surname) {
        setPersonSurnames(surname);
        return this;
    }

    public UpdatePersonRequest setPersonMobileNumber(String mobilenumber) {
        setPersonMobilenumbers(mobilenumber);
        return this;
    }
}
