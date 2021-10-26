package br.ifpr.agenda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {


    @GetMapping("/login")
    public String getLoginPage(){
        return "inicial/login";
    }
}
