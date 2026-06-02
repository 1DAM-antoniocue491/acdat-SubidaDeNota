package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class UserController {
    private Logger log = Logger.getLogger("UserControllerLog");
    List<Usuario> usuarioList;

    public UserController(){
        usuarioList = new ArrayList<Usuario>();
    }

    @GetMapping
    public String viewForm(Model m) {
        m.addAttribute("usuario", new Usuario());
        m.addAttribute("roles", Usuario.ROLE.values());

        return "index";
    }

    @PostMapping("/registro")
    public String processForm(Model m, @ModelAttribute("usuario") Usuario user) {
        log.info("Usuario procesado: " + user.getNombreUsuario());


        boolean usuarioExistente =false;

        for(Usuario usuario : usuarioList){
            if(usuario.getNombreUsuario().equalsIgnoreCase(user.getNombreUsuario())){
                usuarioExistente=true;
            }
        }

        if (!usuarioExistente) {
            usuarioList.add(user);
            log.info("Usuario agregado: " + user.getNombreUsuario());
        } else {
            log.info("Usuario ya existe: " + user.getNombreUsuario());
        }

        m.addAttribute("usuarios", usuarioList);
        return "users";
    }

    @PostMapping("/registro/delete")
    public String deleteUser(@RequestParam("nombreUsuario") String username, Model m) {
        log.info("Usuario borrado: " + username);


        usuarioList.removeIf(u -> u.getNombreUsuario().equalsIgnoreCase(username));

        m.addAttribute("usuarios", usuarioList);
        return "users";
    }

    /*
    // OPCIÓN ALTERNATIVA: Uso de ModelAndView
    @PostMapping("/registro")
    public ModelAndView processForm(@ModelAttribute("usuario") Usuario user) {
        ModelAndView m = new ModelAndView("user");

        m.addObject("user", user);
        m.addObject("fecha", LocalDate.now());

        return m;
    }
    */
}
