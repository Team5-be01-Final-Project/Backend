package com.sales.BPS.msystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/Bps")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public String loginSuccess() {
        return "redirect:/bpm";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String employeeLogin(@RequestParam Integer empCode, @RequestParam String empPw, Model model, HttpSession session) {
        boolean isLogin = employeeService.loginEmployee(empCode, empPw);

        if (isLogin) {
            session.setAttribute("empCode", empCode);
            return "redirect:/list";
        } else {
            model.addAttribute("error", "Failed to login.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutEmployee(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.listAllEmployee();
        model.addAttribute("employees", employees);
        return "employee_list";
    }


}
