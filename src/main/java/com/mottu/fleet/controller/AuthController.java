package com.mottu.fleet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Email ou senha inválidos");
        }
        
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso");
        }
        
            return "login";
    }
    
    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}
