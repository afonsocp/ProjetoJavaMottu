package com.mottu.fleet.service;

import com.mottu.fleet.domain.Patio;
import com.mottu.fleet.dto.PatioForm;
import com.mottu.fleet.repository.PatioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatioService {
    
    private final PatioRepository patioRepository;
    
    public Page<Patio> findAll(String nome, Pageable pageable) {
        return patioRepository.findByFilters(nome, pageable);
    }
    
    public List<Patio> findAll() {
        return patioRepository.findAll();
    }
    
    public Optional<Patio> findById(Long id) {
        return patioRepository.findById(id);
    }
    
    public Patio save(PatioForm form) {
        Patio patio = form.toEntity();
        return patioRepository.save(patio);
    }
    
    public Patio update(Long id, PatioForm form) {
        Patio patioExistente = patioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado: " + id));
        
        patioExistente.setNome(form.getNome());
        patioExistente.setEndereco(form.getEndereco());
        patioExistente.setCapacidadeOpcional(form.getCapacidadeOpcional());
        patioExistente.setObservacoes(form.getObservacoes());
        
        return patioRepository.save(patioExistente);
    }
    
    public void delete(Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado: " + id));
        
        // Verificar se pátio tem alocações
        if (!patio.getAlocacoesOrigem().isEmpty() || !patio.getAlocacoesDevolucao().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir pátio com alocações vinculadas");
        }
        
        patioRepository.delete(patio);
    }
}
