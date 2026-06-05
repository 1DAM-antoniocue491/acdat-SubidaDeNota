package iesmm.ad.ad_t5_04.config;

import iesmm.ad.ad_t5_04.model.Empleado;
import iesmm.ad.ad_t5_04.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    public DataLoader(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Empleado empleado1 = new Empleado(null, "Juan Pérez", "juan.perez@example.com");
        Empleado empleado2 = new Empleado(null, "María García", "maria.garcia@example.com");
        Empleado empleado3 = new Empleado(null, "Carlos López", "carlos.lopez@example.com");
        Empleado empleado4 = new Empleado(null, "Ana Martínez", "ana.martinez@example.com");
        Empleado empleado5 = new Empleado(null, "David Rodríguez", "david.rodriguez@example.com");

        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado2);
        empleadoRepository.save(empleado3);
        empleadoRepository.save(empleado4);
        empleadoRepository.save(empleado5);

        System.out.println("✓ Datos de prueba cargados: 5 empleados creados correctamente");
    }
}
