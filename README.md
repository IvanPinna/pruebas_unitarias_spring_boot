Este proyecto es un proyecto de prueba de cara a diseñar pruebas haciendo uso de Mockito. 

Actualmente el código más relevante se encuentra en el fichero empleadoServiceTest.java 


# Pruebas Unitarias con Spring Boot y Mockito

Este repositorio contiene ejemplos prácticos de **pruebas unitarias en Spring Boot** utilizando **Mockito** y **JUnit 5**.  
El objetivo es mostrar cómo aislar dependencias y validar la lógica de negocio sin necesidad de conectarse a una base de datos real.

## 🚀 Tecnologías utilizadas
- Java 17
- Spring Boot 3.5.5
- JUnit 5
- Mockito
- Lombok
- H2 Database (solo para pruebas)

## 📂 Contenido
En este proyecto encontrarás ejemplos de pruebas para un servicio de empleados:

- **Guardar un empleado** (`saveEmpleado`)  
  - Caso exitoso  
  - Caso de excepción cuando ya existe el email  

- **Listar empleados** (`getAllEmpleados`)  
  - Lista con elementos  
  - Lista vacía  

- **Actualizar un empleado** (`updateEmpleado`)  

- **Eliminar un empleado** (`deleteEmpleado`)  

## 🧪 Ejemplo de prueba con Mockito

```java
@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    @Test
    void guardarEmpleadoTest() {
        Empleado empleado = Empleado.builder()
            .nombre("Juan")
            .apellido("Perez")
            .email("juanperez@email.com")
            .build();

        given(empleadoRepository.save(empleado)).willReturn(empleado);

        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        assertNotNull(empleadoGuardado);
    }
}
