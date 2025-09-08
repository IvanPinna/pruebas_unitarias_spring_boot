package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services.EmpleadoService;


@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoService.saveEmpleado(empleado);
    }

    @GetMapping
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        ResponseEntity<Empleado> response = null;
        
        if (empleado == null) {
            response = ResponseEntity.notFound().build();
        }else{
            response = ResponseEntity.ok(empleado);
        }

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado empleadoActualizado = empleadoService.updateEmpleado(id, empleado);
        ResponseEntity<Empleado> response = null;
        
        if (empleadoActualizado == null) {
            response = ResponseEntity.notFound().build();
        }else{
            response = ResponseEntity.ok(empleadoActualizado);
        }

        return response;
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.ok("Empleado eliminado con exito");
    }
}
