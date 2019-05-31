package com.johnny.web.controller;

import com.johnny.mysql.entity.Role;
import com.johnny.mysql.model.RoleQo;
import com.johnny.mysql.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
@RequestMapping("/role")
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleRepository roleRepository;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) {
        model.addAttribute("user", user);
        return "role/index";
    }

    @RequestMapping(value = "/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        Role role = null;
        if (roleRepository.findById(id).isPresent()) {
            role = roleRepository.findById(id).get();
        }
        model.addAttribute("role", role);
        return "role/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Role> getList(RoleQo roleQo) {
        try {
            Pageable pageable = PageRequest.of(roleQo.getPage(), roleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
            return roleRepository.findByName(roleQo.getName() == null ? "%" : "%" + roleQo.getName() + "%", pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create() {
        return "role/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Role role) {
        roleRepository.save(role);
        logger.info("新增->ID=" + role.getId());
        return "1";
    }

    @RequestMapping(value = "/edit/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        Role role = null;
        if (roleRepository.findById(id).isPresent()) {
            role = roleRepository.findById(id).get();
        }
        model.addAttribute("role", role);
        return "role/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public String update(Role role) {
        roleRepository.save(role);
        logger.info("修改->ID=" + role.getId());
        return "1";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        roleRepository.deleteById(id);
        logger.info("删除->ID=" + id);
        return "1";
    }

}
