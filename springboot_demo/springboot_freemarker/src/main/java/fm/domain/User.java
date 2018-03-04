package fm.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 0:42
 */
@Data
@ApiModel("用户基本信息")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("姓名")
    private String passWord;
    @ApiModelProperty("性别")
    private UserSexEnum userSex;
    @ApiModelProperty("昵称")
    private String nickName;

    public User() {
        super();
    }

    public User(String userName, String passWord, UserSexEnum userSex) {
        super();
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }
}