package application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorMessages {
    public static final String FIELDS_NOT_FILED = "All fields are required";
    public static final String NAME_ALREADY_EXIST = "The item with name \"%s\" already exist";
    public static final String ID_NOT_FOUND = "The item with email \"%s\" is not found";
    public static final String EMAIL_ALREADY_EXIST = "The user with email \"%s\" already exist";
    public static final String NULL_POINT_ID = "List of skill IDs can not contains \"null\"";
}
