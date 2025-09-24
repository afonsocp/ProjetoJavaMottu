package com.mottu.fleet.controller;

import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.dto.MotoForm;
import com.mottu.fleet.service.MotoService;
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
@RequestMapping("/motos")
@RequiredArgsConstructor
public class MotoController {
    
    private final MotoService motoService;
    
    @GetMapping
    public String list(@RequestParam(value = "placa", required = false) String placa,
                      @RequestParam(value = "modelo", required = false) String modelo,
                      @RequestParam(value = "status", required = false) Moto.StatusMoto status,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sort", defaultValue = "placa") String sort,
                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Moto> motos = motoService.findAll(placa, modelo, status, pageable);
        
        model.addAttribute("motos", motos);
        model.addAttribute("placa", placa);
        model.addAttribute("modelo", modelo);
        model.addAttribute("status", status);
        model.addAttribute("statusOptions", Moto.StatusMoto.values());
        
        return "motos/list";
    }
    
    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String createForm(Model model) {
        model.addAttribute("motoForm", new MotoForm());
        model.addAttribute("moto", new Moto()); // Para o título do formulário
        return "motos/form-create-simple";
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String create(@Valid @ModelAttribute MotoForm motoForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("moto", new Moto()); // Para o título do formulário
            return "motos/form";
        }
        
        try {
            motoService.save(motoForm);
            redirectAttributes.addFlashAttribute("success", "Moto cadastrada com sucesso!");
            return "redirect:/motos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/motos/novo";
        }
    }
    
    @GetMapping("/{id}/editar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String editForm(@PathVariable Long id, Model model) {
        Moto moto = motoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        model.addAttribute("motoForm", MotoForm.fromEntity(moto));
        model.addAttribute("moto", moto); // Para o título do formulário
        return "motos/form-simple";
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute MotoForm motoForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            Moto moto = motoService.findById(id).orElse(new Moto());
            model.addAttribute("moto", moto); // Para o título do formulário
            return "motos/form";
        }
        
        try {
            motoService.update(id, motoForm);
            redirectAttributes.addFlashAttribute("success", "Moto atualizada com sucesso!");
            return "redirect:/motos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/motos/" + id + "/editar";
        }
    }
    
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Moto moto = motoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        model.addAttribute("moto", moto);
        return "motos/view";
    }
    
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/motos";
    }
}
