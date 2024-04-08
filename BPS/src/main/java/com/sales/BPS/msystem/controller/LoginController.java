package com.sales.BPS.msystem.controller;

import com.sales.BPS.config.CookieUtil;
import com.sales.BPS.msystem.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sales.BPS.msystem.service.EmployeeService;

@RestController
@RequestMapping("/api")
@Tag(name = "System API", description = "시스템관리 API입니다.")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    @Tag(name = "System API")
    @Operation(summary = "로그인",description = "로그인 시도 후 결과 반환")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {

        System.out.println(loginRequest.getEmpCode()+","+loginRequest.getEmpPw());

        boolean isValidUser = employeeService.loginEmployee(loginRequest.getEmpCode(), loginRequest.getEmpPw());
        if (isValidUser) {
            Integer empCode = loginRequest.getEmpCode();
            // 쿠키 추가
            CookieUtil.createCookie(response, "empCode", empCode.toString(), 60 * 60, false, true, "None");
            CookieUtil.createCookie(response, "empName", employeeService.findByEmpCode(empCode).getEmpName(), 60 * 60, false, true, "None");
            CookieUtil.createCookie(response, "empImg", employeeService.findByEmpCode(empCode).getEmpImg(),  60 * 60, false, true, "None");
            CookieUtil.createCookie(response, "empAuthCode", employeeService.findByEmpCode(empCode).getAuthority().getAuthCode(),  60 * 60, false, true, "None");
            CookieUtil.createCookie(response, "deptCode", employeeService.findByEmpCode(empCode).getDepartment().getDeptCode(),  60 * 60, false, true, "None");
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/login-error")
    @Tag(name = "System API")
    @Operation(summary = "로그인",description = "로그인 시도 후 실패 전달")
    public ResponseEntity<?> loginError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // 쿠키 무효화
        CookieUtil.invalidateCookie(response, "empCode");
        CookieUtil.invalidateCookie(response, "empName");
        CookieUtil.invalidateCookie(response, "empImg");
        CookieUtil.invalidateCookie(response, "empAuthCode");
        CookieUtil.invalidateCookie(response, "deptCode");
        return ResponseEntity.ok("Logout successful");
    }

//    // 쿠키 무효화를 위한 메소드
//    private void invalidateCookies(HttpServletResponse response) {
//        String[] cookieNames = {"empCode", "empName", "empImg", "empAuthCode"};
//        for (String cookieName : cookieNames) {
//            Cookie cookie = new Cookie(cookieName, null);
//            cookie.setPath("/");
//            cookie.setMaxAge(0); // 쿠키 즉시 만료
//            response.addCookie(cookie);
//        }
//    }
}