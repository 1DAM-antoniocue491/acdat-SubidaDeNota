package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Empleado;
import iesmm.ad.ad_t5_04.repository.EmpleadoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private static final Logger log = Logger.getLogger("EmpleadoControllerLog");

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping("/")
    public String listarEmpleados(Model m) {
        m.addAttribute("empleados", empleadoRepository.findAll());
        return "empleados/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model m) {
        m.addAttribute("empleado", new Empleado());
        m.addAttribute("modoEdicion", false);
        return "empleados/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model m) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con id: " + id));
        m.addAttribute("empleado", empleado);
        m.addAttribute("modoEdicion", true);
        return "empleados/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(
            @Valid @ModelAttribute("empleado") Empleado empleado,
            BindingResult bindingResult,
            Model m) {

        if (bindingResult.hasErrors()) {
            m.addAttribute("modoEdicion", empleado.getId() != null);
            return "empleados/formulario";
        }

        empleadoRepository.save(empleado);
        log.info("Empleado guardado: " + empleado.getNombre() + " (id: " + empleado.getId() + ")");
        return "redirect:/empleados";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
        log.info("Empleado eliminado con id: " + id);
        return "redirect:/empleados";
    }
}
