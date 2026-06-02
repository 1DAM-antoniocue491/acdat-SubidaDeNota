package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Producto;
import iesmm.ad.ad_t5_04.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/producto")
public class ProductController {
    private Logger log = Logger.getLogger("ProductControllerLog");
    private final UserController usuarioController;
    private List<Producto> catalogoProductos;

    public ProductController(UserController usuarioController) {
        this.usuarioController = usuarioController;
        this.catalogoProductos = new ArrayList<>();

        // Productos precargados en el catálogo global
        Producto p1 = new Producto();
        p1.setCod("A001");
        p1.setNombre("Teclado Gaming");
        p1.setDescripcion("Teclado mecánico RGB para gaming");
        p1.setFabricante("Logitech");
        p1.setPrecio(79.99f);
        p1.setUnidades(25);
        catalogoProductos.add(p1);

        Producto p2 = new Producto();
        p2.setCod("A002");
        p2.setNombre("Ratón Óptico");
        p2.setDescripcion("Ratón inalámbrico de alta precisión");
        p2.setFabricante("Razer");
        p2.setPrecio(49.99f);
        p2.setUnidades(15);
        catalogoProductos.add(p2);

        Producto p3 = new Producto();
        p3.setCod("B001");
        p3.setNombre("Monitor 27");
        p3.setDescripcion("Monitor IPS 4K UHD");
        p3.setFabricante("Samsung");
        p3.setPrecio(299.99f);
        p3.setUnidades(8);
        catalogoProductos.add(p3);

        Producto p4 = new Producto();
        p4.setCod("B002");
        p4.setNombre("Auriculares");
        p4.setDescripcion("Auriculares con cancelación de ruido");
        p4.setFabricante("Sony");
        p4.setPrecio(149.99f);
        p4.setUnidades(20);
        catalogoProductos.add(p4);

        Producto p5 = new Producto();
        p5.setCod("B003");
        p5.setNombre("Webcam HD");
        p5.setDescripcion("Cámara web 1080p con micrófono");
        p5.setFabricante("Logitech");
        p5.setPrecio(59.99f);
        p5.setUnidades(30);
        catalogoProductos.add(p5);

        Producto p6 = new Producto();
        p6.setCod("C001");
        p6.setNombre("SSD 1TB");
        p6.setDescripcion("Disco sólido interno NVMe");
        p6.setFabricante("Kingston");
        p6.setPrecio(89.99f);
        p6.setUnidades(12);
        catalogoProductos.add(p6);
    }

    public List<Producto> getCatalogoProductos() {
        return catalogoProductos;
    }

    @GetMapping("/catalogo")
    public String verCatalogo(Model m) {
        m.addAttribute("productos", catalogoProductos);
        return "catalogo";
    }

    @GetMapping("/asignar/{nombreUsuario}")
    public String mostrarFormularioAsignacion(@PathVariable String nombreUsuario, Model m) {
        Usuario alumno = usuarioController.getUsuarioList().stream()
            .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
            .findFirst()
            .orElse(null);

        if (alumno != null) {
            m.addAttribute("alumno", alumno);
            m.addAttribute("productosDisponibles", catalogoProductos);
            return "asignar_producto";
        }
        return "redirect:/";
    }

    @PostMapping("/asignar/{nombreUsuario}")
    public String asignarProducto(
            @PathVariable String nombreUsuario,
            @RequestParam("codProducto") String codProducto,
            Model m) {

        Usuario alumno = usuarioController.getUsuarioList().stream()
            .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
            .findFirst()
            .orElse(null);

        if (alumno != null) {
            Producto producto = catalogoProductos.stream()
                .filter(p -> p.getCod().equals(codProducto))
                .findFirst()
                .orElse(null);

            if (producto != null) {
                // Verificar si ya está asignado
                boolean yaAsignado = alumno.getProductos().stream()
                    .anyMatch(p -> p.getCod().equals(codProducto));

                if (!yaAsignado) {
                    alumno.addProducto(producto);
                    log.info("Producto " + codProducto + " asignado a " + alumno.getNombreCompleto());
                } else {
                    log.info("El producto " + codProducto + " ya está asignado a " + alumno.getNombreCompleto());
                }
            }
        }

        return "redirect:/alumno/" + nombreUsuario + "/productos";
    }

    @PostMapping("/desasignar/{nombreUsuario}")
    public String desasignarProducto(
            @PathVariable String nombreUsuario,
            @RequestParam("cod") String cod,
            Model m) {

        Usuario alumno = usuarioController.getUsuarioList().stream()
            .filter(u -> u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
            .findFirst()
            .orElse(null);

        if (alumno != null) {
            alumno.getProductos().removeIf(p -> p.getCod().equals(cod));
            log.info("Producto " + cod + " desasignado de " + alumno.getNombreCompleto());
        }

        return "redirect:/alumno/" + nombreUsuario + "/productos";
    }
}
