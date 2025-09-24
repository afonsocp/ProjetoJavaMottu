package com.mottu.fleet.service;

import com.mottu.fleet.domain.Role;
import com.mottu.fleet.domain.Usuario;
import com.mottu.fleet.repository.RoleRepository;
import com.mottu.fleet.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario save(Usuario usuario) {
        // Verificar se email já existe (exceto na edição)
        if (usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + usuario.getEmail());
        }
        
        // Criptografar senha se for nova
        if (usuario.getId() == null || !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        
        // Verificar se email já existe em outro usuário
        Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email já cadastrado: " + usuario.getEmail());
        }
        
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setAtivo(usuario.getAtivo());
        usuarioExistente.setRoles(usuario.getRoles());
        
        // Atualizar senha apenas se informada
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        return usuarioRepository.save(usuarioExistente);
    }
    
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        
        usuarioRepository.delete(usuario);
    }
    
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
