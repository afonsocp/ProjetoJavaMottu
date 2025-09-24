package com.mottu.fleet.controller;

import com.mottu.fleet.domain.Motorista;
import com.mottu.fleet.dto.MotoristaForm;
import com.mottu.fleet.service.MotoristaService;
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
@RequestMapping("/motoristas")
@RequiredArgsConstructor
public class MotoristaController {
    
    private final MotoristaService motoristaService;
    
    @GetMapping
    public String list(@RequestParam(value = "nome", required = false) String nome,
                      @RequestParam(value = "cpf", required = false) String cpf,
                      @RequestParam(value = "ativo", required = false) Boolean ativo,
                      @RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size,
                      @RequestParam(value = "sort", defaultValue = "nome") String sort,
                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Motorista> motoristas = motoristaService.findAll(nome, cpf, ativo, pageable);
        
        model.addAttribute("motoristas", motoristas);
        model.addAttribute("nome", nome);
        model.addAttribute("cpf", cpf);
        model.addAttribute("ativo", ativo);
        
        return "motoristas/list";
    }
    
    @GetMapping("/novo")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String createForm(Model model) {
        model.addAttribute("motoristaForm", new MotoristaForm());
        model.addAttribute("motorista", new Motorista()); // Para o título do formulário
        return "motoristas/form";
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String create(@Valid @ModelAttribute MotoristaForm motoristaForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("motorista", new Motorista()); // Para o título do formulário
            return "motoristas/form";
        }
        
        try {
            motoristaService.save(motoristaForm);
            redirectAttributes.addFlashAttribute("success", "Motorista cadastrado com sucesso!");
            return "redirect:/motoristas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/motoristas/novo";
        }
    }
    
    @GetMapping("/{id}/editar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String editForm(@PathVariable Long id, Model model) {
        Motorista motorista = motoristaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + id));
        
        model.addAttribute("motoristaForm", MotoristaForm.fromEntity(motorista));
        model.addAttribute("motorista", motorista); // Para o título do formulário
        return "motoristas/form";
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String update(@PathVariable Long id, 
                        @Valid @ModelAttribute MotoristaForm motoristaForm, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            Motorista motorista = motoristaService.findById(id).orElse(new Motorista());
            model.addAttribute("motorista", motorista); // Para o título do formulário
            return "motoristas/form";
        }
        
        try {
            motoristaService.update(id, motoristaForm);
            redirectAttributes.addFlashAttribute("success", "Motorista atualizado com sucesso!");
            return "redirect:/motoristas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/motoristas/" + id + "/editar";
        }
    }
    
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Motorista motorista = motoristaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + id));
        
        model.addAttribute("motorista", motorista);
        return "motoristas/view";
    }
    
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoristaService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Motorista excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/motoristas";
    }
}
