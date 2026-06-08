package iesmm.ad.ad_t5_04.config;

import iesmm.ad.ad_t5_04.model.Empleado;
import iesmm.ad.ad_t5_04.model.EstadoTarea;
import iesmm.ad.ad_t5_04.model.Tarea;
import iesmm.ad.ad_t5_04.repository.EmpleadoRepository;
import iesmm.ad.ad_t5_04.repository.TareaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    private final TareaRepository tareaRepository;

    public DataLoader(EmpleadoRepository empleadoRepository, TareaRepository tareaRepository) {
        this.empleadoRepository = empleadoRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear empleados
        Empleado empleado1 = new Empleado(null, "Juan Perez", "juan.perez@example.com");
        Empleado empleado2 = new Empleado(null, "Maria Garcia", "maria.garcia@example.com");
        Empleado empleado3 = new Empleado(null, "Carlos Lopez", "carlos.lopez@example.com");
        Empleado empleado4 = new Empleado(null, "Ana Martinez", "ana.martinez@example.com");
        Empleado empleado5 = new Empleado(null, "David Rodriguez", "david.rodriguez@example.com");

        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado2);
        empleadoRepository.save(empleado3);
        empleadoRepository.save(empleado4);
        empleadoRepository.save(empleado5);

        // Crear tareas de ejemplo
        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Desarrollar módulo de autenticación");
        tarea1.setDescripcion("Implementar login y registro de usuarios");
        tarea1.setFechaInicio(LocalDateTime.now());
        tarea1.setFechaFin(LocalDateTime.now().plusDays(5));
        tarea1.setEstado(EstadoTarea.EN_PROCESO);
        tarea1.setEmpleado(empleado1);

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Diseñar base de datos");
        tarea2.setDescripcion("Crear modelo entidad-relación y script SQL");
        tarea2.setFechaInicio(LocalDateTime.now().minusDays(2));
        tarea2.setFechaFin(LocalDateTime.now().plusDays(3));
        tarea2.setEstado(EstadoTarea.FINALIZADA);
        tarea2.setEmpleado(empleado2);

        Tarea tarea3 = new Tarea();
        tarea3.setTitulo("Revisión de código");
        tarea3.setDescripcion("Revisar PRs pendientes del sprint");
        tarea3.setFechaInicio(LocalDateTime.now());
        tarea3.setFechaFin(LocalDateTime.now().plusDays(1));
        tarea3.setEstado(EstadoTarea.PENDIENTE);
        tarea3.setEmpleado(empleado3);

        Tarea tarea4 = new Tarea();
        tarea4.setTitulo("Configurar CI/CD");
        tarea4.setDescripcion("Setup de GitHub Actions para deploy automático");
        tarea4.setFechaInicio(LocalDateTime.now().minusDays(1));
        tarea4.setFechaFin(LocalDateTime.now().plusDays(7));
        tarea4.setEstado(EstadoTarea.PENDIENTE);
        tarea4.setEmpleado(empleado4);

        Tarea tarea5 = new Tarea();
        tarea5.setTitulo("Documentación API");
        tarea5.setDescripcion("Escribir documentación de endpoints REST");
        tarea5.setFechaInicio(LocalDateTime.now());
        tarea5.setFechaFin(LocalDateTime.now().plusDays(4));
        tarea5.setEstado(EstadoTarea.EN_PROCESO);
        tarea5.setEmpleado(empleado5);

        tareaRepository.save(tarea1);
        tareaRepository.save(tarea2);
        tareaRepository.save(tarea3);
        tareaRepository.save(tarea4);
        tareaRepository.save(tarea5);

        System.out.println("Datos de prueba cargados: 5 empleados y 5 tareas creados correctamente");
    }
}
