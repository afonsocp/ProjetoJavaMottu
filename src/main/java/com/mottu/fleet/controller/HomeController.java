package com.mottu.fleet.controller;

import com.mottu.fleet.service.AlocacaoService;
import com.mottu.fleet.service.ManutencaoService;
import com.mottu.fleet.service.MotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final MotoService motoService;
    private final AlocacaoService alocacaoService;
    private final ManutencaoService manutencaoService;
    
    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        // Estatísticas básicas para o dashboard (valores fixos por enquanto)
        model.addAttribute("totalMotos", 4);
        model.addAttribute("totalMotoristas", 3);
        model.addAttribute("alocacoesAtivas", 1);
        model.addAttribute("manutencoesAbertas", 1);
        model.addAttribute("usuario", authentication != null ? authentication.getName() : "Anônimo");
        
            return "home-modern";
    }
}
