package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springboot.model.User;
import springboot.server.UserServer;

@Api(value = "用户相关的接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServer server;

    @RequestMapping("/add")
    @ApiOperation(notes = "add user", value = "add a user", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "psw", paramType = "query", dataType = "string")
    })
    public String add(User user) {
        return "hello " + server.add(user);
    }

    @RequestMapping("/all")
    @ApiOperation(notes = "find all user", value = "查询所有有，目前只返回数量", httpMethod = "GET")
    public String find() {
        return "" + server.findAll().size();
    }
}
