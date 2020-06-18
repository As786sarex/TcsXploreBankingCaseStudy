package com.xplore.casestudy.bankapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:home";
    }

    @GetMapping("/error")
    public String error(){
        return "error_controller";
    }


}
