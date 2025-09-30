package com.mottu.fleet.service;

import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.dto.MotoForm;
import com.mottu.fleet.repository.MotoRepository;
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
public class MotoService {
    
    private final MotoRepository motoRepository;
    
    public Page<Moto> findAll(String placa, String modelo, Moto.StatusMoto status, Pageable pageable) {
        return motoRepository.findByFilters(placa, modelo, status, pageable);
    }
    
    public List<Moto> findAll() {
        return motoRepository.findAll();
    }
    
    public List<Moto> findDisponiveis() {
        return motoRepository.findDisponiveis();
    }
    
    public Optional<Moto> findById(Long id) {
        return motoRepository.findById(id);
    }
    
    public Optional<Moto> findByPlaca(String placa) {
        return motoRepository.findByPlaca(placa);
    }
    
    public Moto save(MotoForm form) {
        Moto moto = form.toEntity();
        
        // Verificar se placa já existe (exceto na edição)
        if (moto.getId() == null && motoRepository.existsByPlaca(moto.getPlaca())) {
            throw new IllegalArgumentException("Placa já cadastrada: " + moto.getPlaca());
        }
        
        return motoRepository.save(moto);
    }
    
    public Moto update(Long id, MotoForm form) {
        Moto motoExistente = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        // Verificar se placa já existe em outra moto
        Optional<Moto> motoComMesmaPlaca = motoRepository.findByPlaca(form.getPlaca());
        if (motoComMesmaPlaca.isPresent() && !motoComMesmaPlaca.get().getId().equals(id)) {
            throw new IllegalArgumentException("Placa já cadastrada: " + form.getPlaca());
        }
        
        motoExistente.setPlaca(form.getPlaca());
        motoExistente.setModelo(form.getModelo());
        motoExistente.setAno(form.getAno());
        motoExistente.setKmAtual(form.getKmAtual());
        motoExistente.setStatus(form.getStatus());
        motoExistente.setChassi(form.getChassi());
        motoExistente.setRenavam(form.getRenavam());
        motoExistente.setObservacoes(form.getObservacoes());
        
        return motoRepository.save(motoExistente);
    }
    
    public void delete(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        // Verificar se moto pode ser excluída
        if (moto.isAlocada()) {
            throw new IllegalArgumentException("Não é possível excluir moto alocada");
        }
        
        if (moto.isEmManutencao()) {
            throw new IllegalArgumentException("Não é possível excluir moto em manutenção");
        }
        
        motoRepository.delete(moto);
    }
    
    public void atualizarStatus(Long id, Moto.StatusMoto status) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        moto.setStatus(status);
        motoRepository.save(moto);
    }
    
    public void atualizarKm(Long id, Long kmAtual) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
        
        if (kmAtual != null && kmAtual > moto.getKmAtual()) {
            moto.setKmAtual(kmAtual);
            motoRepository.save(moto);
        }
    }
    
    public long countAll() {
        return motoRepository.count();
    }
    
    public long countByStatus(String status) {
        return motoRepository.countByStatus(Moto.StatusMoto.valueOf(status));
    }
    
    public long countMotoristas() {
        // Este método deveria estar no MotoristaService, mas por simplicidade
        // vamos implementar aqui por enquanto
        return 0; // Será implementado quando necessário
    }
}
