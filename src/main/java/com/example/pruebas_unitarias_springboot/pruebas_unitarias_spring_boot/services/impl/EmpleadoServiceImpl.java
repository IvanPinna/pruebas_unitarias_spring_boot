package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.exception.ResourceNotFoundException;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.repositories.EmpleadoRepository;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services.EmpleadoService;

public class EmpleadoServiceImpl implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = empleadoRepository.findByEmail(empleado.getEmail());

        if (empleadoGuardado.isPresent()) {
            throw new ResourceNotFoundException("El email ya existe");
        }

        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public Empleado updateEmpleado(Long id, Empleado empleado) {
        return empleadoRepository.findById(id).map(emp -> {
            emp.setNombre(empleado.getNombre());
            emp.setApellido(empleado.getApellido());
            emp.setEmail(empleado.getEmail());
            return empleadoRepository.save(emp);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }


}
