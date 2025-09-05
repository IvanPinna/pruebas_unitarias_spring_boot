package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services;

import java.util.List;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;

public interface EmpleadoService {
    Empleado saveEmpleado(Empleado empleado);
    List<Empleado> getAllEmpleados();
    Empleado getEmpleadoById(Long id);
    Empleado updateEmpleado(Long id, Empleado empleado);
    void deleteEmpleado(Long id);
}
