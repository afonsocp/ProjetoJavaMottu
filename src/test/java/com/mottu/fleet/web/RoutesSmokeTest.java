package com.mottu.fleet.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class RoutesSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRedirectToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessHomeWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessMotosList() throws Exception {
        mockMvc.perform(get("/motos"))
                .andExpect(status().isOk())
                .andExpect(view().name("motos/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessMotoristasList() throws Exception {
        mockMvc.perform(get("/motoristas"))
                .andExpect(status().isOk())
                .andExpect(view().name("motoristas/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessPatiosList() throws Exception {
        mockMvc.perform(get("/patios"))
                .andExpect(status().isOk())
                .andExpect(view().name("patios/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessAlocacoesList() throws Exception {
        mockMvc.perform(get("/alocacoes"))
                .andExpect(status().isOk())
                .andExpect(view().name("alocacoes/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAccessManutencoesList() throws Exception {
        mockMvc.perform(get("/manutencoes"))
                .andExpect(status().isOk())
                .andExpect(view().name("manutencoes/list"));
    }

    @Test
    void shouldAccessLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
