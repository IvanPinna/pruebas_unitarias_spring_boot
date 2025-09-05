package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByEmail(String email);
} 
