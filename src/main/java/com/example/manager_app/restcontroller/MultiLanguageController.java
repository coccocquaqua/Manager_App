package com.example.manager_app.restcontroller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MultiLanguageController {
    @Autowired
    private MessageSource messageSource; //để lấy thông tin từ file properties

    @RequestMapping("/language")
    public String index(Model model, HttpServletRequest request) { //HttpServletRequest để lấy thông tin ngôn ngữ từ request
        String message = messageSource.getMessage("hello", null, "default message", request.getLocale()); //lấy thông tin từ file properties
        // request.getLocale() dùng để lấy thông tin ngôn ngữ từ request
        model.addAttribute("message", message); //thêm thông tin vào model để hiển thị ra view
        return "multilanguage";
    }
}
