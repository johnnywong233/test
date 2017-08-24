package grammar.enums;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 23:40
 */
public enum AuditNotifyStatus implements EnumMessage {
    Sms(2, "短信"),
    Mail(4, "邮箱"),
    SmsAndMail(6, "短信和邮箱"),;
    private final Integer _code;
    private final String _message;

    AuditNotifyStatus(Integer code, String message) {
        _code = code;
        _message = message;
    }

    @Override
    public Integer getValue() {
        return _code;
    }

    @Override
    public String getMessage() {
        return _message;
    }
}
