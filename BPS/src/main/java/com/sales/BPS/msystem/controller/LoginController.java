package com.sales.BPS.msystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entity.Business;
import com.test.service.BusinessService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/business")
public class BusinessLoginController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "Blogin_form";
    }

    @PostMapping("/login")
    public String Blogin(@RequestParam String bId, @RequestParam String bpwd, Model model, HttpSession session) {
        boolean isLogin = businessService.loginBusiness(bId, bpwd);

        if (isLogin) {
            session.setAttribute("bId", bId);
            System.out.println("session 정보: " + (String) session.getAttribute("bId"));
            return "BforPrivate"; // 로그인 성공시 유저 메뉴로 이동
        } else {
            model.addAttribute("error", "로그인을 실패했습니다.");
            return "Blogin_form"; // 로그인 실패 시 로그인 폼을 다시 표시
        }
    }

    @GetMapping("/logout")
    public String logoutBusiness(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/business/login"; //로그인 페이지로 이동
    }

    @GetMapping("/BforPrivate")
    public String showBforPrivate(Model model, HttpSession session) {
        String bId = (String) session.getAttribute("bId");

        if (bId != null) {
            Business currentbusiness = businessService.getBusinessByCode(bId);
            model.addAttribute("business", currentbusiness);
        } else {
            System.out.println("유효하지 않은 접근입니다.");
            return "redirect:/business/login"; //로그인 페이지로 이동
        }
        return "BforPrivate";
    }
}
