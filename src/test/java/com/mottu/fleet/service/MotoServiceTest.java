package com.mottu.fleet.service;

import com.mottu.fleet.domain.Moto;
import com.mottu.fleet.dto.MotoForm;
import com.mottu.fleet.repository.MotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;

    @InjectMocks
    private MotoService motoService;

    private MotoForm motoForm;
    private Moto moto;

    @BeforeEach
    void setUp() {
        motoForm = new MotoForm();
        motoForm.setPlaca("ABC1234");
        motoForm.setModelo("Honda CG 160");
        motoForm.setAno(2023);
        motoForm.setKmAtual(15000L);
        motoForm.setStatus(Moto.StatusMoto.DISPONIVEL);

        moto = new Moto();
        moto.setId(1L);
        moto.setPlaca("ABC1234");
        moto.setModelo("Honda CG 160");
        moto.setAno(2023);
        moto.setKmAtual(15000L);
        moto.setStatus(Moto.StatusMoto.DISPONIVEL);
    }

    @Test
    void shouldSaveMotoSuccessfully() {
        // Given
        when(motoRepository.existsByPlaca("ABC1234")).thenReturn(false);
        when(motoRepository.save(any(Moto.class))).thenReturn(moto);

        // When
        Moto result = motoService.save(motoForm);

        // Then
        assertNotNull(result);
        assertEquals("ABC1234", result.getPlaca());
        assertEquals("Honda CG 160", result.getModelo());
        verify(motoRepository).save(any(Moto.class));
    }

    @Test
    void shouldThrowExceptionWhenPlacaAlreadyExists() {
        // Given
        when(motoRepository.existsByPlaca("ABC1234")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> motoService.save(motoForm));
        
        assertEquals("Placa já cadastrada: ABC1234", exception.getMessage());
        verify(motoRepository, never()).save(any(Moto.class));
    }

    @Test
    void shouldUpdateMotoSuccessfully() {
        // Given
        motoForm.setId(1L);
        when(motoRepository.findById(1L)).thenReturn(Optional.of(moto));
        when(motoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(moto));
        when(motoRepository.save(any(Moto.class))).thenReturn(moto);

        // When
        Moto result = motoService.update(1L, motoForm);

        // Then
        assertNotNull(result);
        verify(motoRepository).save(any(Moto.class));
    }

    @Test
    void shouldThrowExceptionWhenMotoNotFoundForUpdate() {
        // Given
        when(motoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> motoService.update(1L, motoForm));
        
        assertEquals("Moto não encontrada: 1", exception.getMessage());
        verify(motoRepository, never()).save(any(Moto.class));
    }

    @Test
    void shouldDeleteMotoSuccessfully() {
        // Given
        when(motoRepository.findById(1L)).thenReturn(Optional.of(moto));

        // When
        motoService.delete(1L);

        // Then
        verify(motoRepository).delete(moto);
    }

    @Test
    void shouldThrowExceptionWhenMotoNotFoundForDelete() {
        // Given
        when(motoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> motoService.delete(1L));
        
        assertEquals("Moto não encontrada: 1", exception.getMessage());
        verify(motoRepository, never()).delete(any(Moto.class));
    }
}
