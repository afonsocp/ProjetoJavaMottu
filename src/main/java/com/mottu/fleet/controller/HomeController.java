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
        // Estatísticas dinâmicas do dashboard
        long totalMotos = motoService.countAll();
        long totalMotoristas = motoService.countMotoristas();
        long alocacoesAtivas = alocacaoService.countAtivas();
        long manutencoesAbertas = manutencaoService.countAbertas();
        
        // Estatísticas de status das motos
        long motosDisponiveis = motoService.countByStatus("DISPONIVEL");
        long motosAlocadas = motoService.countByStatus("ALOCADA");
        long motosEmManutencao = motoService.countByStatus("MANUTENCAO");
        
        // Cálculo de percentuais
        double percentualDisponiveis = totalMotos > 0 ? (motosDisponiveis * 100.0 / totalMotos) : 0;
        double percentualAlocadas = totalMotos > 0 ? (motosAlocadas * 100.0 / totalMotos) : 0;
        double percentualManutencao = totalMotos > 0 ? (motosEmManutencao * 100.0 / totalMotos) : 0;
        
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("totalMotoristas", totalMotoristas);
        model.addAttribute("alocacoesAtivas", alocacoesAtivas);
        model.addAttribute("manutencoesAbertas", manutencoesAbertas);
        model.addAttribute("motosDisponiveis", motosDisponiveis);
        model.addAttribute("motosAlocadas", motosAlocadas);
        model.addAttribute("motosEmManutencao", motosEmManutencao);
        model.addAttribute("percentualDisponiveis", Math.round(percentualDisponiveis));
        model.addAttribute("percentualAlocadas", Math.round(percentualAlocadas));
        model.addAttribute("percentualManutencao", Math.round(percentualManutencao));
        model.addAttribute("usuario", authentication != null ? authentication.getName() : "Anônimo");
        
        return "home";
    }
}
