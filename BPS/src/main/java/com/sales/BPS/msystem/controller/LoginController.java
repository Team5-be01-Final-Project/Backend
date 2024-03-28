package com.sales.BPS.msystem.controller;

import com.sales.BPS.msystem.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sales.BPS.msystem.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {

        System.out.println(loginRequest.getEmpCode()+","+loginRequest.getEmpPw());

        boolean isValidUser = employeeService.loginEmployee(loginRequest.getEmpCode(), loginRequest.getEmpPw());
        if (isValidUser) {
            Cookie empCodeCookie = new Cookie("empCode", loginRequest.getEmpCode().toString());
            empCodeCookie.setMaxAge(12 * 60 * 60); // 쿠키의 유효 기간을 12시간으로 설정
            empCodeCookie.setPath("/"); // 쿠키의 경로 설정(모든 경로에서 쿠키 사용 가능)
            response.addCookie(empCodeCookie);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/login-error")
    public ResponseEntity<?> loginError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 쿠키 무효화
        Cookie cookie = new Cookie("empCode", null); //empCode 쿠키 만료
        cookie.setPath("/"); // 쿠키의 경로 설정
        cookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
        response.addCookie(cookie);


        return ResponseEntity.ok("Logout successful");
    }

}