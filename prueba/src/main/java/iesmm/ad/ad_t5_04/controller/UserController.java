package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class UserController {
    private Logger log = Logger.getLogger("UserControllerLog");
    private List<Usuario> usuarioList;

    public UserController(){
        usuarioList = new ArrayList<>();

        // Alumnos precargados
        usuarioList.add(new Usuario("juan", "Juan García", "juan@example.com", Usuario.ROLE.alumno));
        usuarioList.add(new Usuario("maria", "María López", "maria@example.com", Usuario.ROLE.alumno));
        usuarioList.add(new Usuario("pedro", "Pedro Martínez", "pedro@example.com", Usuario.ROLE.alumno));
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    @GetMapping
    public String viewDashboard(Model m) {
        m.addAttribute("usuarios", usuarioList);
        return "dashboard";
    }

    @GetMapping("/alumno/{nombreUsuario}/productos")
    public String verProductosAlumno(@PathVariable String nombreUsuario, Model m) {
        Usuario alumno = usuarioList.stream()
            .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
            .findFirst()
            .orElse(null);

        if (alumno != null) {
            m.addAttribute("alumno", alumno);
            return "productos_alumno";
        }
        return "redirect:/";
    }
}
