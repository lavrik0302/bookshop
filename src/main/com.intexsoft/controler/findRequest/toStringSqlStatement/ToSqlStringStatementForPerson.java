package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindPersonRequest;

import java.util.UUID;

public class ToSqlStringStatementForPerson {
    public String toSQLStringStatement(FindPersonRequest findPersonRequest) {
        StringBuilder sb=new StringBuilder();
        if (!findPersonRequest.getPersonIds().isEmpty()) {
            sb.append("person_id ");
            if (findPersonRequest.getPersonIds().size() > 1) {
                sb.append("in (");
                for (UUID person_id : findPersonRequest.getPersonIds()) {
                    sb.append("'").append(person_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonIds().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonNames().isEmpty()) {
            sb.append("name ");
            if (findPersonRequest.getPersonNames().size() > 1) {
                sb.append("in (");
                for (String name : findPersonRequest.getPersonNames()) {
                    sb.append("'").append(name).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonNames().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonSurnames().isEmpty()) {
            sb.append("surname ");
            if (findPersonRequest.getPersonSurnames().size() > 1) {
                sb.append("in (");
                for (String surname : findPersonRequest.getPersonSurnames()) {
                    sb.append("'").append(surname).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonSurnames().get(0)).append("' AND ");
            }
        }
        if (!findPersonRequest.getPersonMobilenumbers().isEmpty()) {
            sb.append("mobilenumber ");
            if (findPersonRequest.getPersonMobilenumbers().size() > 1) {
                sb.append("in (");
                for (String mobilenumber :findPersonRequest.getPersonMobilenumbers()) {
                    sb.append("'").append(mobilenumber).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonRequest.getPersonMobilenumbers().get(0)).append("' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
