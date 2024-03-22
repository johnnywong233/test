package com.johnny.web.controller;

import com.johnny.mysql.entity.Department;
import com.johnny.mysql.model.DepartmentQo;
import com.johnny.mysql.repository.DepartmentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentRepository departmentRepository;

    @RequestMapping("/index")
    public String index(ModelMap model, Principal user) {
        model.addAttribute("user", user);
        return "department/index";
    }

    @RequestMapping(value = "/{id}")
    public String show(ModelMap model, @PathVariable Long id) {
        Department department = null;
        if (departmentRepository.findById(id).isPresent()) {
            department = departmentRepository.findById(id).get();
        }
        model.addAttribute("department", department);
        return "department/show";
    }

    @RequestMapping(value = "/list")
    public Page<Department> getList(DepartmentQo departmentQo) {
        try {
            Pageable pageable = PageRequest.of(departmentQo.getPage(), departmentQo.getSize(), Sort.by(Sort.Direction.ASC, "id"));
            return departmentRepository.findByName(departmentQo.getName() == null ? "%" : "%" + departmentQo.getName() + "%", pageable);
        } catch (Exception e) {
            log.error("getList fail", e);
        }
        return null;
    }

    @RequestMapping("/new")
    public String create() {
        return "department/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Department department) {
        departmentRepository.save(department);
        log.info("新增->ID=" + department.getId());
        return "1";
    }

    @RequestMapping(value = "/edit/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        Department department = null;
        if (departmentRepository.findById(id).isPresent()) {
            department = departmentRepository.findById(id).get();
        }
        model.addAttribute("department", department);
        return "department/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String update(Department department) {
        departmentRepository.save(department);
        log.info("修改->ID=" + department.getId());
        return "1";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        log.info("删除->ID=" + id);
        return "1";
    }

}
