package com.mottu.fleet.controller;

import com.mottu.fleet.domain.Alocacao;
import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.domain.Motorista;
import com.mottu.fleet.domain.Patio;
import com.mottu.fleet.dto.AlocacaoForm;
import com.mottu.fleet.dto.DevolucaoForm;
import com.mottu.fleet.service.AlocacaoService;
import com.mottu.fleet.service.MotoService;
import com.mottu.fleet.service.MotoristaService;
import com.mottu.fleet.service.PatioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alocacoes")
@RequiredArgsConstructor
public class AlocacaoController {
    
    private final AlocacaoService alocacaoService;
    private final MotoService motoService;
    private final MotoristaService motoristaService;
    private final PatioService patioService;
    
    @GetMapping
    public String list(@RequestParam(value = "motoId", required = false) Long motoId,
                      @RequestParam(value = "motoristaId", required = false) Long motoristaId,
                      @RequestParam(value = "status", required = false) Alocacao.StatusAlocacao status,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sort", defaultValue = "dataHoraSaida") String sort,
                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Alocacao> alocacoes = alocacaoService.findAll(motoId, motoristaId, status, pageable);
        
        model.addAttribute("alocacoes", alocacoes);
        model.addAttribute("motoId", motoId);
        model.addAttribute("motoristaId", motoristaId);
        model.addAttribute("status", status);
        model.addAttribute("statusOptions", Alocacao.StatusAlocacao.values());
        
        return "alocacoes/list";
    }
    
    @GetMapping("/nova")
    public String createForm(@RequestParam(value = "motoId", required = false) Long motoId,
                           @RequestParam(value = "motoristaId", required = false) Long motoristaId,
                           @RequestParam(value = "patioId", required = false) Long patioId,
                           Model model) {
        
        AlocacaoForm form = new AlocacaoForm();
        form.setMotoId(motoId);
        form.setMotoristaId(motoristaId);
        form.setPatioOrigemId(patioId);
        
        model.addAttribute("alocacaoForm", form);
        model.addAttribute("motosDisponiveis", motoService.findDisponiveis());
        model.addAttribute("motoristasAtivos", motoristaService.findAtivosComCnhValida());
        model.addAttribute("patios", patioService.findAll());
        
        return "alocacoes/nova";
    }
    
    @PostMapping
    public String create(@Valid @ModelAttribute AlocacaoForm alocacaoForm, 
                        BindingResult result, 
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dados inválidos");
            return "redirect:/alocacoes/nova";
        }
        
        try {
            alocacaoService.alocar(alocacaoForm);
            redirectAttributes.addFlashAttribute("success", "Moto alocada com sucesso!");
            return "redirect:/alocacoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alocacoes/nova";
        }
    }
    
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Alocacao alocacao = alocacaoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alocação não encontrada: " + id));
        
        model.addAttribute("alocacao", alocacao);
        return "alocacoes/view";
    }
    
    @GetMapping("/{id}/devolucao")
    public String devolucaoForm(@PathVariable Long id, Model model) {
        Alocacao alocacao = alocacaoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alocação não encontrada: " + id));
        
        if (!alocacao.isAtiva()) {
            throw new IllegalArgumentException("Alocação não está ativa para devolução");
        }
        
        model.addAttribute("alocacao", alocacao);
        model.addAttribute("devolucaoForm", new DevolucaoForm());
        model.addAttribute("patios", patioService.findAll());
        
        return "alocacoes/devolucao";
    }
    
    @PostMapping("/{id}/devolucao")
    public String devolver(@PathVariable Long id, 
                          @Valid @ModelAttribute DevolucaoForm devolucaoForm, 
                          BindingResult result, 
                          RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dados inválidos");
            return "redirect:/alocacoes/" + id + "/devolucao";
        }
        
        try {
            alocacaoService.devolver(id, devolucaoForm);
            redirectAttributes.addFlashAttribute("success", "Moto devolvida com sucesso!");
            return "redirect:/alocacoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alocacoes/" + id + "/devolucao";
        }
    }
}
