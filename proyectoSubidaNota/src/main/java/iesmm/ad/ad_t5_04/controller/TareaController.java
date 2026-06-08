package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Empleado;
import iesmm.ad.ad_t5_04.model.EstadoTarea;
import iesmm.ad.ad_t5_04.model.Tarea;
import iesmm.ad.ad_t5_04.repository.EmpleadoRepository;
import iesmm.ad.ad_t5_04.repository.TareaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private static final Logger log = Logger.getLogger("TareaControllerLog");

    private final TareaRepository tareaRepository;
    private final EmpleadoRepository empleadoRepository;

    public TareaController(TareaRepository tareaRepository, EmpleadoRepository empleadoRepository) {
        this.tareaRepository = tareaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping("/")
    public String listarTareas(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "empleadoId", required = false) Long empleadoId,
            Model m) {

        List<Tarea> tareas;

        // Filtrar por estado si se proporciona
        if (estado != null && !estado.isEmpty()) {
            try {
                EstadoTarea estadoTarea = EstadoTarea.valueOf(estado.toUpperCase());
                tareas = tareaRepository.findByEstado(estadoTarea);
            } catch (IllegalArgumentException e) {
                tareas = tareaRepository.findAll();
            }
        } else if (empleadoId != null) {
            // Filtrar por empleado si se proporciona
            tareas = tareaRepository.findByEmpleadoId(empleadoId);
        } else {
            // Sin filtros: todas las tareas
            tareas = tareaRepository.findAll();
        }

        m.addAttribute("tareas", tareas);
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("filtroEstado", estado);
        m.addAttribute("filtroEmpleadoId", empleadoId);

        return "tareas/lista";
    }

    @GetMapping("/pendientes")
    public String tareasPendientes(Model m) {
        m.addAttribute("tareas", tareaRepository.findByEstado(EstadoTarea.PENDIENTE));
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("filtroEstado", "PENDIENTE");
        return "tareas/lista";
    }

    @GetMapping("/en-proceso")
    public String tareasEnProceso(Model m) {
        m.addAttribute("tareas", tareaRepository.findByEstado(EstadoTarea.EN_PROCESO));
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("filtroEstado", "EN_PROCESO");
        return "tareas/lista";
    }

    @GetMapping("/finalizadas")
    public String tareasFinalizadas(Model m) {
        m.addAttribute("tareas", tareaRepository.findByEstado(EstadoTarea.FINALIZADA));
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("filtroEstado", "FINALIZADA");
        return "tareas/lista";
    }

    @GetMapping("/empleado/{empleadoId}")
    public String tareasPorEmpleado(@PathVariable Long empleadoId, Model m) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con id: " + empleadoId));
        m.addAttribute("tareas", tareaRepository.findByEmpleadoId(empleadoId));
        m.addAttribute("empleado", empleado);
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("filtroEmpleadoId", empleadoId);
        return "tareas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model m) {
        Tarea tarea = new Tarea();
        tarea.setEstado(EstadoTarea.PENDIENTE);
        m.addAttribute("tarea", tarea);
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("modoEdicion", false);
        return "tareas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model m) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con id: " + id));
        m.addAttribute("tarea", tarea);
        m.addAttribute("empleados", empleadoRepository.findAll());
        m.addAttribute("estados", EstadoTarea.values());
        m.addAttribute("modoEdicion", true);
        return "tareas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarTarea(
            @Valid @ModelAttribute("tarea") Tarea tarea,
            BindingResult bindingResult,
            Model m) {

        if (bindingResult.hasErrors()) {
            m.addAttribute("empleados", empleadoRepository.findAll());
            m.addAttribute("estados", EstadoTarea.values());
            m.addAttribute("modoEdicion", tarea.getId() != null);
            return "tareas/formulario";
        }

        // Asegurar que el empleado existe si está asignado
        if (tarea.getEmpleado() != null && tarea.getEmpleado().getId() != null) {
            Empleado empleado = empleadoRepository.findById(tarea.getEmpleado().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
            tarea.setEmpleado(empleado);
        }

        tareaRepository.save(tarea);
        log.info("Tarea guardada: " + tarea.getTitulo() + " (id: " + tarea.getId() + ")");
        return "redirect:/tareas/";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaRepository.deleteById(id);
        log.info("Tarea eliminada con id: " + id);
        return "redirect:/tareas/";
    }

    @PostMapping("/{id}/cambiar-estado")
    public String cambiarEstado(@PathVariable Long id, @ModelAttribute("nuevoEstado") String nuevoEstado) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada con id: " + id));

        try {
            EstadoTarea estado = EstadoTarea.valueOf(nuevoEstado.toUpperCase());
            tarea.setEstado(estado);
            tareaRepository.save(tarea);
            log.info("Tarea " + id + " cambió a estado: " + estado);
        } catch (IllegalArgumentException e) {
            log.warning("Estado inválido: " + nuevoEstado);
        }

        return "redirect:/tareas/";
    }
}
