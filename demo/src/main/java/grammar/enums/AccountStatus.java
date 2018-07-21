package grammar.enums;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 23:39
 */
public enum AccountStatus implements EnumMessage {
    //
    Init(0, "初始化"),
    Ready(1, "正常"),
    ChangePassword(2, "需要修改密码"),
    Frozen(4, "冻结"),
    Disabled(64, "禁用");
    private final Integer code;
    private final String message;

    AccountStatus(Integer code, String message) {
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
