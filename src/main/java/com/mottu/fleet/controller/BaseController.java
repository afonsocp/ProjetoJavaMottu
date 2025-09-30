package com.mottu.fleet.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe base para controllers com funcionalidades comuns
 * Elimina violação DRY e melhora manutenibilidade
 */
public abstract class BaseController<T, F> {
    
    protected String handleCreateForm(Model model, F form, T entity, String formView) {
        model.addAttribute(getFormAttributeName(), form);
        model.addAttribute(getEntityAttributeName(), entity);
        return formView;
    }
    
    protected String handleCreate(@Valid F form, 
                                 BindingResult result, 
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 String successMessage,
                                 String errorRedirect,
                                 String successRedirect) {
        
        if (result.hasErrors()) {
            model.addAttribute(getEntityAttributeName(), createNewEntity());
            return getFormView();
        }
        
        try {
            saveEntity(form);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return successRedirect;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return errorRedirect;
        }
    }
    
    protected String handleUpdate(Long id, 
                                 @Valid F form, 
                                 BindingResult result, 
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 String successMessage,
                                 String errorRedirect,
                                 String successRedirect) {
        
        if (result.hasErrors()) {
            T entity = findEntityById(id).orElse(createNewEntity());
            model.addAttribute(getEntityAttributeName(), entity);
            return getFormView();
        }
        
        try {
            updateEntity(id, form);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return successRedirect;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return errorRedirect;
        }
    }
    
    protected String handleDelete(Long id, RedirectAttributes redirectAttributes, String successMessage) {
        try {
            deleteEntity(id);
            redirectAttributes.addFlashAttribute("success", successMessage);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return getListRedirect();
    }
    
    // Métodos abstratos que devem ser implementados pelas classes filhas
    protected abstract String getFormAttributeName();
    protected abstract String getEntityAttributeName();
    protected abstract String getFormView();
    protected abstract String getListRedirect();
    protected abstract T createNewEntity();
    protected abstract java.util.Optional<T> findEntityById(Long id);
    protected abstract void saveEntity(F form);
    protected abstract void updateEntity(Long id, F form);
    protected abstract void deleteEntity(Long id);
}
