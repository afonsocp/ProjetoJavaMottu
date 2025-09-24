package com.mottu.fleet.service;

import com.mottu.fleet.domain.Manutencao;
import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.dto.ManutencaoAberturaForm;
import com.mottu.fleet.dto.ManutencaoFechamentoForm;
import com.mottu.fleet.repository.ManutencaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManutencaoService {
    
    private final ManutencaoRepository manutencaoRepository;
    private final MotoService motoService;
    private final AlocacaoService alocacaoService;
    
    public Page<Manutencao> findAll(Long motoId, Manutencao.StatusManutencao status, Pageable pageable) {
        return manutencaoRepository.findByFilters(motoId, status, pageable);
    }
    
    public List<Manutencao> findAllAbertas() {
        return manutencaoRepository.findAllAbertas();
    }
    
    public Optional<Manutencao> findById(Long id) {
        return manutencaoRepository.findById(id);
    }
    
    public Optional<Manutencao> findAbertaByMotoId(Long motoId) {
        return manutencaoRepository.findAbertaByMotoId(motoId);
    }
    
    public Manutencao abrirManutencao(ManutencaoAberturaForm form, Authentication authentication) {
        Moto moto = motoService.findById(form.getMotoId())
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + form.getMotoId()));
        
        // Validações de negócio
        if (moto.isEmManutencao()) {
            throw new IllegalArgumentException("Moto já está em manutenção");
        }
        
        if (moto.isAlocada()) {
            throw new IllegalArgumentException("Não é possível abrir manutenção para moto alocada");
        }
        
        // Criar manutenção
        Manutencao manutencao = new Manutencao();
        manutencao.setMoto(moto);
        manutencao.setAbertoEm(LocalDateTime.now());
        manutencao.setAbertoPor(authentication.getName());
        manutencao.setMotivo(form.getMotivo());
        manutencao.setStatus(Manutencao.StatusManutencao.ABERTA);
        
        // Atualizar status da moto
        motoService.atualizarStatus(moto.getId(), Moto.StatusMoto.MANUTENCAO);
        
        return manutencaoRepository.save(manutencao);
    }
    
    public Manutencao fecharManutencao(Long manutencaoId, ManutencaoFechamentoForm form, Authentication authentication) {
        Manutencao manutencao = manutencaoRepository.findById(manutencaoId)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada: " + manutencaoId));
        
        // Validações de negócio
        if (!manutencao.isAberta()) {
            throw new IllegalArgumentException("Manutenção não está aberta para fechamento");
        }
        
        // Atualizar manutenção
        manutencao.setStatus(Manutencao.StatusManutencao.FECHADA);
        manutencao.setFechadoEm(LocalDateTime.now());
        manutencao.setFechadoPor(authentication.getName());
        manutencao.setObservacoes(form.getObservacoes());
        
        // Verificar se moto pode voltar a disponível
        Moto moto = manutencao.getMoto();
        
        // Verificar se moto tem alocação ativa
        boolean temAlocacaoAtiva = alocacaoService.findAtivaByMotoId(moto.getId()).isPresent();
        
        if (!temAlocacaoAtiva) {
            motoService.atualizarStatus(moto.getId(), Moto.StatusMoto.DISPONIVEL);
        }
        
        return manutencaoRepository.save(manutencao);
    }
}
