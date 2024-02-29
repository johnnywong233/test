package errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //
    NULL_OBJ("LUO001", "object is null"),
    ERROR_ADD_USER("LUO002", "error occurred while add user"),
    LOGIN_VERIFY_FAILURE("LUO003", "login authorization failed, please check username/password"),
    UNKNOWN_ERROR("LUO999", "system busy, please try again...");

    private final String value;
    private final String desc;
}
