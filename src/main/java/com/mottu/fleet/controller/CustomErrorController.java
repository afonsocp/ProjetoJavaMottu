package com.mottu.fleet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Página não encontrada");
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                String errorMessage = "Erro interno do servidor";
                
                if (exception != null) {
                    Throwable throwable = (Throwable) exception;
                    errorMessage = throwable.getMessage();
                    model.addAttribute("exception", throwable.getClass().getName());
                    model.addAttribute("stackTrace", getStackTrace(throwable));
                }
                
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorMessage", errorMessage);
                return "error/500";
            }
        }
        
        model.addAttribute("errorCode", "Erro");
        model.addAttribute("errorMessage", "Ocorreu um erro inesperado");
        return "error/500";
    }
    
    private String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}

