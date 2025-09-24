package com.mottu.fleet.service;

import com.mottu.fleet.domain.Alocacao;
import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.domain.Motorista;
import com.mottu.fleet.domain.Patio;
import com.mottu.fleet.dto.AlocacaoForm;
import com.mottu.fleet.dto.DevolucaoForm;
import com.mottu.fleet.repository.AlocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlocacaoService {
    
    private final AlocacaoRepository alocacaoRepository;
    private final MotoService motoService;
    private final MotoristaService motoristaService;
    private final PatioService patioService;
    
    public Page<Alocacao> findAll(Long motoId, Long motoristaId, Alocacao.StatusAlocacao status, Pageable pageable) {
        return alocacaoRepository.findByFilters(motoId, motoristaId, status, pageable);
    }
    
    public List<Alocacao> findAllAtivas() {
        return alocacaoRepository.findAllAtivas();
    }
    
    public Optional<Alocacao> findById(Long id) {
        return alocacaoRepository.findById(id);
    }
    
    public Optional<Alocacao> findAtivaByMotoId(Long motoId) {
        return alocacaoRepository.findAtivaByMotoId(motoId);
    }
    
    public Optional<Alocacao> findAtivaByMotoristaId(Long motoristaId) {
        return alocacaoRepository.findAtivaByMotoristaId(motoristaId);
    }
    
    public Alocacao alocar(AlocacaoForm form) {
        // Buscar entidades
        Moto moto = motoService.findById(form.getMotoId())
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + form.getMotoId()));
        
        Motorista motorista = motoristaService.findById(form.getMotoristaId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado: " + form.getMotoristaId()));
        
        Patio patioOrigem = patioService.findById(form.getPatioOrigemId())
                .orElseThrow(() -> new IllegalArgumentException("Pátio não encontrado: " + form.getPatioOrigemId()));
        
        // Validações de negócio
        if (!moto.isDisponivel()) {
            throw new IllegalArgumentException("Moto não está disponível para alocação. Status atual: " + moto.getStatus());
        }
        
        if (!motorista.isAtivo()) {
            throw new IllegalArgumentException("Motorista não está ativo");
        }
        
        if (!motorista.isCnhValida()) {
            throw new IllegalArgumentException("CNH do motorista está vencida");
        }
        
        // Verificar se motorista já tem alocação ativa
        Optional<Alocacao> alocacaoAtivaMotorista = findAtivaByMotoristaId(motorista.getId());
        if (alocacaoAtivaMotorista.isPresent()) {
            throw new IllegalArgumentException("Motorista já possui uma alocação ativa");
        }
        
        // Criar alocação
        Alocacao alocacao = new Alocacao();
        alocacao.setMoto(moto);
        alocacao.setMotorista(motorista);
        alocacao.setPatioOrigem(patioOrigem);
        alocacao.setDataHoraSaida(LocalDateTime.now());
        alocacao.setChecklistSaida(form.getChecklistSaida());
        alocacao.setStatus(Alocacao.StatusAlocacao.ATIVA);
        
        // Atualizar status da moto
        motoService.atualizarStatus(moto.getId(), Moto.StatusMoto.ALOCADA);
        
        return alocacaoRepository.save(alocacao);
    }
    
    public Alocacao devolver(Long alocacaoId, DevolucaoForm form) {
        Alocacao alocacao = alocacaoRepository.findById(alocacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Alocação não encontrada: " + alocacaoId));
        
        // Validações de negócio
        if (!alocacao.isAtiva()) {
            throw new IllegalArgumentException("Alocação não está ativa para devolução");
        }
        
        Patio patioDevolucao = patioService.findById(form.getPatioDevolucaoId())
                .orElseThrow(() -> new IllegalArgumentException("Pátio de devolução não encontrado: " + form.getPatioDevolucaoId()));
        
        // Atualizar alocação
        alocacao.setStatus(Alocacao.StatusAlocacao.ENCERRADA);
        alocacao.setDataHoraDevolucao(LocalDateTime.now());
        alocacao.setChecklistDevolucao(form.getChecklistDevolucao());
        alocacao.setPatioDevolucao(patioDevolucao);
        alocacao.setKmDevolucao(form.getKmDevolucao());
        
        // Atualizar quilometragem da moto se informada
        if (form.getKmDevolucao() != null) {
            motoService.atualizarKm(alocacao.getMoto().getId(), form.getKmDevolucao());
        }
        
        // Verificar se moto pode voltar a disponível
        Moto moto = alocacao.getMoto();
        if (!moto.isEmManutencao()) {
            motoService.atualizarStatus(moto.getId(), Moto.StatusMoto.DISPONIVEL);
        }
        
        return alocacaoRepository.save(alocacao);
    }
}
