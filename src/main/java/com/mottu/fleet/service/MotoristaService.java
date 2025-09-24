package com.mottu.fleet.service;

import com.mottu.fleet.domain.Motorista;
import com.mottu.fleet.dto.MotoristaForm;
import com.mottu.fleet.repository.MotoristaRepository;
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
public class MotoristaService {
    
    private final MotoristaRepository motoristaRepository;
    
    public Page<Motorista> findAll(String nome, String cpf, Boolean ativo, Pageable pageable) {
        return motoristaRepository.findByFilters(nome, cpf, ativo, pageable);
    }
    
    public List<Motorista> findAll() {
        return motoristaRepository.findAll();
    }
    
    public List<Motorista> findAtivosComCnhValida() {
        return motoristaRepository.findAtivosComCnhValida();
    }
    
    public Optional<Motorista> findById(Long id) {
        return motoristaRepository.findById(id);
    }
    
    public Optional<Motorista> findByCpf(String cpf) {
        return motoristaRepository.findByCpf(cpf);
    }
    
    public Motorista save(MotoristaForm form) {
        Motorista motorista = form.toEntity();
        
        // Verificar se CPF já existe (exceto na edição)
        if (motorista.getId() == null && motoristaRepository.existsByCpf(motorista.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + motorista.getCpf());
        }
        
        return motoristaRepository.save(motorista);
    }
    
    public Motorista update(Long id, MotoristaForm form) {
        Motorista motoristaExistente = motoristaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + id));
        
        // Verificar se CPF já existe em outro motorista
        Optional<Motorista> motoristaComMesmoCpf = motoristaRepository.findByCpf(form.getCpf());
        if (motoristaComMesmoCpf.isPresent() && !motoristaComMesmoCpf.get().getId().equals(id)) {
            throw new IllegalArgumentException("CPF já cadastrado: " + form.getCpf());
        }
        
        motoristaExistente.setNome(form.getNome());
        motoristaExistente.setCpf(form.getCpf());
        motoristaExistente.setCnh(form.getCnh());
        motoristaExistente.setValidadeCnh(form.getValidadeCnh());
        motoristaExistente.setTelefone(form.getTelefone());
        motoristaExistente.setEmail(form.getEmail());
        motoristaExistente.setAtivo(form.getAtivo());
        
        return motoristaRepository.save(motoristaExistente);
    }
    
    public void delete(Long id) {
        Motorista motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + id));
        
        // Verificar se motorista tem alocações ativas
        if (!motorista.getAlocacoes().isEmpty()) {
            boolean temAlocacaoAtiva = motorista.getAlocacoes().stream()
                    .anyMatch(alocacao -> alocacao.isAtiva());
            
            if (temAlocacaoAtiva) {
                throw new IllegalArgumentException("Não é possível excluir motorista com alocação ativa");
            }
        }
        
        motoristaRepository.delete(motorista);
    }
}
