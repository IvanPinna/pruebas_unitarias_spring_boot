package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.exception.ResourceNotFoundException;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.repositories.EmpleadoRepository;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services.impl.EmpleadoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class empleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juanperez@email.com").build();
    }
    
    @Test
    void guardarEmpleadoTest() {   
     given(empleadoRepository.save(empleado)).willReturn(empleado);

     Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);
     assertNotNull(empleadoGuardado);
    }

    @Test
    void guardarEmpleadoExceptionTest(){
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.of(empleado));  //simulamos que el empleado ya existe 
        assertThrows(ResourceNotFoundException.class,()->{
            empleadoService.saveEmpleado(empleado);});

        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    void listarEmpleadosTest(){
        Empleado empleado1 = Empleado.builder()
                .nombre("Carlos")   
                .apellido("Lopez")
                .email("carloslopez@email.com").build();
        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1));

        List<Empleado> empleados = empleadoService.getAllEmpleados();
        assertNotNull(empleados);
        assert(empleados.size() == 2);
    }

    @Test
    void listarEmpleadosVaciaTest(){
        given(empleadoRepository.findAll()).willReturn(List.of());
        
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        assertNotNull(empleados);
        assert(empleados.size() == 0);
    }

    @Test
    void updateEmpleadoTest(){
        Long id = 1L;
        given(empleadoRepository.findById(id)).willReturn(Optional.of(empleado));
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setNombre("Pedro");

        Empleado empleadoActualizado = empleadoService.updateEmpleado(id, empleado);

        assertNotNull(empleadoActualizado);
        assert(empleadoActualizado.getNombre().equals("Pedro"));
    }

    @Test
    void deleteEmpleadoTest(){
        Long id = 1L;
        willDoNothing().given(empleadoRepository).deleteById(id);
        empleadoService.deleteEmpleado(id);
        
        verify(empleadoRepository).deleteById(id);
    }
}
