package com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.model.Empleado;
import com.example.pruebas_unitarias_springboot.pruebas_unitarias_spring_boot.services.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmpleadoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void guardarEmpleadoTest() throws Exception {
        Empleado empleado = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juanperez@email.com").build();
        
        given(empleadoService.saveEmpleado(any(Empleado.class))).willReturn(empleado); 

        ResultActions response = mockMvc.perform(post("/api/empleados")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(empleado)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
                .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
                .andExpect(jsonPath("$.email", is(empleado.getEmail())));   
    }

    @Test
    void listarEmpleadosTest() throws Exception{
        List<Empleado> empleados = List.of(
            Empleado.builder().nombre("Juan").apellido("Perez").email("juanperez@email.com").build(),
            Empleado.builder().nombre("Ana").apellido("Gomez").email("anagomez@email.com").build(),
            Empleado.builder().nombre("Luis").apellido("Martinez").email("luismartinez@email.com").build(),
            Empleado.builder().nombre("Marta").apellido("Lopez").email("martalopez@email.com").build(),
            Empleado.builder().nombre("Carlos").apellido("Garcia").email("carlosgarcia@email.com").build()
        );

        given(empleadoService.getAllEmpleados()).willReturn(empleados);

        ResultActions response = mockMvc.perform(get("/api/empleados")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(empleados)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(empleados.size())));
    }

    @Test
    void obtenerEmpleadoPorIdTest() throws Exception{
        Long empleadoId = 1L;
        Empleado empleado = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juanperez@email.com").build();

        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(empleado);
    
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}", empleadoId)
                .contentType("application/json"));
                
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
                .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
                .andExpect(jsonPath("$.email", is(empleado.getEmail())));
    }

        @Test
        void obtenerEmpleadoPorIdNoEncontradoTest() throws Exception{
                Long empleadoId = 1L;

                given(empleadoService.getEmpleadoById(empleadoId)).willReturn(null);

                ResultActions response = mockMvc.perform(get("/api/empleados/{id}", empleadoId)
                        .contentType("application/json"));
                        
                response.andExpect(status().isNotFound())
                        .andDo(print());
        }

        @Test
        void actualizarEmpleadoTest() throws Exception {
                Long empleadoId = 1L;
                Empleado empleadoExistente = Empleado.builder()
                                .nombre("Juan")
                                .apellido("Perez")
                                .email("juanperez@gmail.com").build();
                Empleado empleadoActualizado = Empleado.builder()
                                .nombre("Juan Carlos")
                                .apellido("Perez")
                                .email("juanperez@gmail.com").build();
                given(empleadoService.getEmpleadoById(empleadoId)).willReturn(empleadoExistente);
                given(empleadoService.updateEmpleado(eq(empleadoId), any(Empleado.class))).willReturn(empleadoActualizado);
                
                ResultActions response = mockMvc.perform(put("/api/empleados/{id}", empleadoId)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(empleadoActualizado)));

                response.andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(jsonPath("$.nombre", is(empleadoActualizado.getNombre())))
                                .andExpect(jsonPath("$.apellido", is(empleadoActualizado.getApellido())))
                                .andExpect(jsonPath("$.email", is(empleadoActualizado.getEmail())));
        }

         @Test
        void actualizarEmpleadoNoEncontradoTest() throws Exception {
                Long empleadoId = 1L;
                Empleado empleadoExistente = Empleado.builder()
                                .nombre("Juan")
                                .apellido("Perez")
                                .email("juanperez@gmail.com").build();
                Empleado empleadoActualizado = Empleado.builder()
                                .nombre("Juan Carlos")
                                .apellido("Perez")
                                .email("juanperez@gmail.com").build();
                given(empleadoService.getEmpleadoById(empleadoId)).willReturn(empleadoExistente);
                given(empleadoService.updateEmpleado(eq(empleadoId), any(Empleado.class))).willReturn(null);
                
                ResultActions response = mockMvc.perform(put("/api/empleados/{id}", empleadoId)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(empleadoActualizado)));

                response.andExpect(status().isNotFound())
                                .andDo(print());
        }

        @Test
        void eliminarEmpleadoTest() throws Exception {
                Long empleadoId = 1L;
                
                willDoNothing().given(empleadoService).deleteEmpleado(empleadoId);
                ResultActions response = mockMvc.perform(delete("/api/empleados/{id}/eliminar", empleadoId)
                                .contentType("application/json"));

                response.andExpect(status().isOk())
                                .andDo(print());
        }
}
