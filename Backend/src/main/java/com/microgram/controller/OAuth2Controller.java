package com.microgram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuth2Controller {

    @GetMapping("/oauth2/redirect")
    public String oauth2Redirect(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "oauth2-success";
    }

    @GetMapping("/oauth2/error")
    public String oauth2Error(@RequestParam("error") String error, Model model) {
        model.addAttribute("error", error);
        return "oauth2-error";
    }
}