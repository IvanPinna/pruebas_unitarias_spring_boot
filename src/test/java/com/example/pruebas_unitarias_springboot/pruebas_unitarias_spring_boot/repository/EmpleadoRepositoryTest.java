package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.repositories.EmpleadoRepository;

@DataJpaTest //Comprueba componentes solo de la capa de persistencia
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado, empleado2;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juanperez@email.com").build();
        empleado2 = Empleado.builder()
                .nombre("Ana")
                .apellido("Gomez")
                .email("").build();
    }

    @Test
    void guardarEmpleadoTest() {
        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        //Then
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void listarEmpleadosTest() {
        empleadoRepository.save(empleado); 
        empleadoRepository.save(empleado2);

        List<Empleado> empleados = empleadoRepository.findAll();
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @Test
    void obtenerEmpleadoPorIdTest() {
        empleadoRepository.save(empleado);
        Empleado empleadoBuscado = empleadoRepository.findById(empleado.getId()).orElse(null);

        assertThat(empleadoBuscado).isNotNull();
        assertThat(empleadoBuscado.getId()).isEqualTo(empleado.getId());
    }

    @Test
    void actualizarEmpleadoTest() {
        empleadoRepository.save(empleado);
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).orElse(null);

        empleadoGuardado.setNombre("PruebaTest");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        assertThat(empleadoActualizado.getNombre()).isEqualTo("PruebaTest");
    }

    @Test
    void eliminarEmpleadoTest() {
        empleadoRepository.save(empleado);
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).orElse(null);
        empleadoRepository.delete(empleadoGuardado);

        Empleado empleadoInexistente = empleadoRepository.findById(empleado.getId()).orElse(null);
        assertThat(empleadoInexistente).isNull();
    }
}
