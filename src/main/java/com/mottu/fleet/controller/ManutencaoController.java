package com.mottu.fleet.controller;

import com.mottu.fleet.domain.Manutencao;
import com.mottu.fleet.dto.ManutencaoAberturaForm;
import com.mottu.fleet.dto.ManutencaoFechamentoForm;
import com.mottu.fleet.service.ManutencaoService;
import com.mottu.fleet.service.MotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {
    
    private final ManutencaoService manutencaoService;
    private final MotoService motoService;
    
    @GetMapping
    public String list(@RequestParam(value = "motoId", required = false) Long motoId,
                      @RequestParam(value = "status", required = false) Manutencao.StatusManutencao status,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sort", defaultValue = "abertoEm") String sort,
                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Manutencao> manutencoes = manutencaoService.findAll(motoId, status, pageable);
        
        model.addAttribute("manutencoes", manutencoes);
        model.addAttribute("motoId", motoId);
        model.addAttribute("status", status);
        model.addAttribute("statusOptions", Manutencao.StatusManutencao.values());
        
        return "manutencoes/list";
    }
    
    @GetMapping("/abrir")
    public String abrirForm(@RequestParam(value = "motoId", required = false) Long motoId,
                           Model model) {
        
        ManutencaoAberturaForm form = new ManutencaoAberturaForm();
        form.setMotoId(motoId);
        
        model.addAttribute("manutencaoForm", form);
        model.addAttribute("motos", motoService.findAll());
        
        return "manutencoes/abrir";
    }
    
    @PostMapping("/abrir")
    public String abrir(@Valid @ModelAttribute ManutencaoAberturaForm manutencaoForm, 
                       BindingResult result, 
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dados inválidos");
            return "redirect:/manutencoes/abrir";
        }
        
        try {
            manutencaoService.abrirManutencao(manutencaoForm, authentication);
            redirectAttributes.addFlashAttribute("success", "Manutenção aberta com sucesso!");
            return "redirect:/manutencoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/manutencoes/abrir";
        }
    }
    
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada: " + id));
        
        model.addAttribute("manutencao", manutencao);
        return "manutencoes/view";
    }
    
    @GetMapping("/{id}/fechar")
    public String fecharForm(@PathVariable Long id, Model model) {
        Manutencao manutencao = manutencaoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada: " + id));
        
        if (!manutencao.isAberta()) {
            throw new IllegalArgumentException("Manutenção não está aberta para fechamento");
        }
        
        model.addAttribute("manutencao", manutencao);
        model.addAttribute("fechamentoForm", new ManutencaoFechamentoForm());
        
        return "manutencoes/fechar";
    }
    
    @PostMapping("/{id}/fechar")
    public String fechar(@PathVariable Long id, 
                        @Valid @ModelAttribute ManutencaoFechamentoForm fechamentoForm, 
                        BindingResult result, 
                        Authentication authentication,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dados inválidos");
            return "redirect:/manutencoes/" + id + "/fechar";
        }
        
        try {
            manutencaoService.fecharManutencao(id, fechamentoForm, authentication);
            redirectAttributes.addFlashAttribute("success", "Manutenção fechada com sucesso!");
            return "redirect:/manutencoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/manutencoes/" + id + "/fechar";
        }
    }
}
