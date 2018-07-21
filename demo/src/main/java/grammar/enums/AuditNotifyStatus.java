package grammar.enums;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 23:40
 */
public enum AuditNotifyStatus implements EnumMessage {
    //
    Sms(2, "短信"),
    Mail(4, "邮箱"),
    SmsAndMail(6, "短信和邮箱");
    private final Integer code;
    private final String message;

    AuditNotifyStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
