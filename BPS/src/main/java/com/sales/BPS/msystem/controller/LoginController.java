package com.sales.BPS.msystem.controller;

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

            Cookie empCodeCookie = new Cookie("empCode", empCode.toString());
            empCodeCookie.setDomain(null);
            empCodeCookie.setHttpOnly(true);
            empCodeCookie.setMaxAge(12 * 60 * 60); // 쿠키의 유효 기간을 12시간으로 설정
            empCodeCookie.setPath("/"); // 쿠키의 경로 설정(모든 경로에서 쿠키 사용 가능)
            empCodeCookie.setSecure(true);
            // SameSite 설정을 위해 별도의 헤더를 추가합니다.
            String cookieValue = "Path=/; HttpOnly; Secure; SameSite=None";
            response.addHeader("Set-Cookie", cookieValue);
            response.addCookie(empCodeCookie);


            Cookie empNameCookie = new Cookie("empName", employeeService.findByEmpCode(empCode).getEmpName());
            empNameCookie.setDomain(null);
            empNameCookie.setHttpOnly(true);
            empNameCookie.setMaxAge(12 * 60 * 60);
            empNameCookie.setPath("/");
            response.addCookie(empNameCookie);
            empNameCookie.setSecure(true);
            // SameSite 설정을 위해 별도의 헤더를 추가합니다.
            String cookieValue1 = "Path=/; HttpOnly; Secure; SameSite=None";
            response.addHeader("Set-Cookie", cookieValue1);
            response.addCookie(empNameCookie);


            Cookie empImgCookie = new Cookie("empImg", employeeService.findByEmpCode(empCode).getEmpImg());
            empImgCookie.setDomain(null);
            empImgCookie.setHttpOnly(true);
            empImgCookie.setMaxAge(12 * 60 * 60);
            empImgCookie.setPath("/");
            response.addCookie(empImgCookie);
            empImgCookie.setSecure(true);
            // SameSite 설정을 위해 별도의 헤더를 추가합니다.
            String cookieValue2 = "Path=/; HttpOnly; Secure; SameSite=None";
            response.addHeader("Set-Cookie", cookieValue2);
            response.addCookie(empImgCookie);


            Cookie empaAuthCodeCookie = new Cookie("empAuthCode",employeeService.findByEmpCode(empCode).getAuthority().getAuthCode());
            empaAuthCodeCookie.setDomain(null);
            empaAuthCodeCookie.setHttpOnly(true);
            empaAuthCodeCookie.setMaxAge(12 * 60 * 60);
            empaAuthCodeCookie.setPath("/");
            response.addCookie(empaAuthCodeCookie);
            empaAuthCodeCookie.setSecure(true);
            // SameSite 설정을 위해 별도의 헤더를 추가합니다.
            String cookieValue3 = "Path=/; HttpOnly; Secure; SameSite=None";
            response.addHeader("Set-Cookie", cookieValue3);
            response.addCookie(empaAuthCodeCookie);

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
        Cookie empCodeCookie = new Cookie("empCode", null); //empCode 쿠키 만료
        empCodeCookie.setPath("/");
        empCodeCookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
        response.addCookie(empCodeCookie);

        Cookie empNameCookie = new Cookie("empName", null); //empCode 쿠키 만료
        empNameCookie.setPath("/");
        empNameCookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
        response.addCookie(empNameCookie);

        Cookie empImgCookie = new Cookie("empImg", null); //empCode 쿠키 만료
        empImgCookie.setPath("/");
        empImgCookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
        response.addCookie(empImgCookie);

        Cookie empaAuthCodeCookie = new Cookie("empAuthCode", null); //empCode 쿠키 만료
        empaAuthCodeCookie.setPath("/");
        empaAuthCodeCookie.setMaxAge(0); // 쿠키의 만료 시간을 0으로 설정하여 즉시 만료
        response.addCookie(empaAuthCodeCookie);

        // 쿠키 무효화 2
//        invalidateCookies(response);

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