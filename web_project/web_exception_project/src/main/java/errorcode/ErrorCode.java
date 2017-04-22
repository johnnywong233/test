package errorcode;

public enum ErrorCode {

    NULL_OBJ("LUO001", "object is null"),
    ERROR_ADD_USER("LUO002", "error occurred while add user"),
    LOGIN_VERIFY_FAILURE("LUO003", "login authorization failed, please check username/password"),
    UNKNOWN_ERROR("LUO999", "system busy, please try again...");

    private String value;
    private String desc;

    private ErrorCode(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}
