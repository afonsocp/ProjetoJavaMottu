package com.mottu.fleet.controller;

import com.mottu.fleet.domain.Patio;
import com.mottu.fleet.dto.PatioForm;
import com.mottu.fleet.service.PatioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patios")
@RequiredArgsConstructor
public class PatioController {
    
    private final PatioService patioService;
    
    @GetMapping
    public String list(@RequestParam(value = "nome", required = false) String nome,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sort", defaultValue = "nome") String sort,
                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Patio> patios = patioService.findAll(nome, pageable);
        
        model.addAttribute("patios", patios);
        model.addAttribute("nome", nome);
        
        return "patios/list";
    }
    
    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String createForm(Model model) {
        model.addAttribute("patioForm", new PatioForm());
        model.addAttribute("patio", new Patio()); // Para o título do formulário
        return "patios/form";
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String create(@Valid @ModelAttribute PatioForm patioForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("patio", new Patio()); // Para o título do formulário
            return "patios/form";
        }
        
        try {
            patioService.save(patioForm);
            redirectAttributes.addFlashAttribute("success", "Pátio cadastrado com sucesso!");
            return "redirect:/patios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patios/novo";
        }
    }
    
    @GetMapping("/{id}/editar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String editForm(@PathVariable Long id, Model model) {
        Patio patio = patioService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado: " + id));
        
        model.addAttribute("patioForm", PatioForm.fromEntity(patio));
        model.addAttribute("patio", patio); // Para o título do formulário
        return "patios/form";
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute PatioForm patioForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            Patio patio = patioService.findById(id).orElse(new Patio());
            model.addAttribute("patio", patio); // Para o título do formulário
            return "patios/form";
        }
        
        try {
            patioService.update(id, patioForm);
            redirectAttributes.addFlashAttribute("success", "Pátio atualizado com sucesso!");
            return "redirect:/patios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/patios/" + id + "/editar";
        }
    }
    
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            patioService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Pátio excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/patios";
    }
}
