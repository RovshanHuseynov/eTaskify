package az.abbbank.cloud.etaskify.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessagesEnum {
    INVALID_PASSWORD("Your password is not valid. Please set at least 6 AlphaNumeric characters"),
    INVALID_COMPANY("Company not found"),
    INVALID_EMPLOYEE("Employee not found"),
    INVALID_TASK("Task not found"),
    INVALID_TASK_STATUS_ENUM("Task status enum not found");

    private final String message;

    public String getMessage() {
        return message;
    }

    public String getMessage(long id) {
        return getMessage(String.valueOf(id));
    }

    public String getMessage(String text) {
        return String.join(" ", text, message);
    }
}
