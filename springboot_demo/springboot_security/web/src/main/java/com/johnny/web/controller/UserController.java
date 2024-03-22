package com.johnny.web.controller;

import com.johnny.mysql.entity.Department;
import com.johnny.mysql.entity.Role;
import com.johnny.mysql.entity.User;
import com.johnny.mysql.model.UserQo;
import com.johnny.mysql.repository.DepartmentRepository;
import com.johnny.mysql.repository.RoleRepository;
import com.johnny.mysql.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private RoleRepository roleRepository;

    @Value("${securityconfig.urlroles}")
    private String urlRoles;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) {
        Authentication authentication = (Authentication) user;
        List<String> userRoles = new ArrayList<>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            userRoles.add(ga.getAuthority());
        }

        boolean newRole = false, editRole = false, deleteRole = false;
        if (!StringUtils.isEmpty(urlRoles)) {
            String[] resources = urlRoles.split(";");
            for (String resource : resources) {
                String[] urls = resource.split("=");
                if (urls[0].indexOf("new") > 0) {
                    String[] newRoles = urls[1].split(",");
                    for (String str : newRoles) {
                        str = str.trim();
                        if (userRoles.contains(str)) {
                            newRole = true;
                            break;
                        }
                    }
                } else if (urls[0].indexOf("edit") > 0) {
                    String[] editRoles = urls[1].split(",");
                    for (String str : editRoles) {
                        str = str.trim();
                        if (userRoles.contains(str)) {
                            editRole = true;
                            break;
                        }
                    }
                } else if (urls[0].indexOf("delete") > 0) {
                    String[] deleteRoles = urls[1].split(",");
                    for (String str : deleteRoles) {
                        str = str.trim();
                        if (userRoles.contains(str)) {
                            deleteRole = true;
                            break;
                        }
                    }
                }
            }
        }

        model.addAttribute("newRole", newRole);
        model.addAttribute("editRole", editRole);
        model.addAttribute("deleteRole", deleteRole);

        model.addAttribute("user", user);
        return "user/index";
    }

    @RequestMapping(value = "/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        User user = new User();
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        }
        model.addAttribute("user", user);
        return "user/show";
    }

    @RequestMapping(value = "/list")
    public Page<User> getList(UserQo userQo) {
        try {
            Pageable pageable = PageRequest.of(userQo.getPage(), userQo.getSize(), Sort.by(Sort.Direction.ASC, "id"));
            return userRepository.findByName(userQo.getName() == null ? "%" : "%" + userQo.getName() + "%", pageable);
        } catch (Exception e) {
            log.error("getList fail", e);
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model, User user) {
        List<Department> departments = departmentRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("departments", departments);
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "user/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user) {
        user.setCreateDate(new Date());
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        user.setPassword(bpe.encode(user.getPassword()));
        userRepository.save(user);
        log.info("新增->ID=" + user.getId());
        return "1";
    }

    @RequestMapping(value = "/edit/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        User user = new User();
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        }
        List<Department> departments = departmentRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        List<Long> rids = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rids.add(role.getId());
        }

        model.addAttribute("user", user);
        model.addAttribute("departments", departments);
        model.addAttribute("roles", roles);
        model.addAttribute("rids", rids);
        return "user/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String update(User user) {
        userRepository.save(user);
        log.info("修改->ID=" + user.getId());
        return "1";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        log.info("删除->ID=" + id);
        return "1";
    }

}