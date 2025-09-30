package com.mottu.fleet.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class SecurityAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldAllowOperatorToAccessMotosList() throws Exception {
        mockMvc.perform(get("/motos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldDenyOperatorToCreateMoto() throws Exception {
        mockMvc.perform(get("/motos/novo"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void shouldAllowManagerToCreateMoto() throws Exception {
        mockMvc.perform(get("/motos/novo"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAllowAdminToCreateMoto() throws Exception {
        mockMvc.perform(get("/motos/novo"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldAllowOperatorToAccessAlocacoes() throws Exception {
        mockMvc.perform(get("/alocacoes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldAllowOperatorToCreateAlocacao() throws Exception {
        mockMvc.perform(get("/alocacoes/nova"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldAllowOperatorToAccessManutencoes() throws Exception {
        mockMvc.perform(get("/manutencoes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "OPERADOR")
    void shouldAllowOperatorToOpenManutencao() throws Exception {
        mockMvc.perform(get("/manutencoes/abrir"))
                .andExpect(status().isOk());
    }
}
