package demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import demo1.model.User;
import demo1.server.UserServer;

@Api(value = "user related interface")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServer server;

    @RequestMapping("/add")
    @ApiOperation(notes = "add user", value = "add one user", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "psw", paramType = "query", dataType = "string")
    })
    public String add(User user) {
        return "hello " + server.add(user);
    }

    /**
     * query all users
     */
    @RequestMapping("/all")
    @ApiOperation(notes = "query all users", value = "query all users, get the num", httpMethod = "GET")
    public String find() {
        return "" + server.findAll().size();
    }
}
