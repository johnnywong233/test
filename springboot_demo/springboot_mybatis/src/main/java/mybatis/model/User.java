package mybatis.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class User {

    private Integer id;

    @ApiModelProperty
    private String username;

    @ApiModelProperty
    private String psw;

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public void setPsw(String psw) {
        this.psw = psw == null ? null : psw.trim();
    }
}