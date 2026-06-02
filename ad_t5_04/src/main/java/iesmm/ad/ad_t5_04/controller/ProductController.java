package iesmm.ad.ad_t5_04.controller;

import iesmm.ad.ad_t5_04.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/producto")
public class ProductController {
    private Logger log = Logger.getLogger("ProductControllerLog");
    private final String PRODUCTO_FORMULARIO = "producto_form";
    private final String PRODUCTO_INFO = "producto_info";

    private List<Producto> productos = new ArrayList<>(); // Lista para almacenar productos

    @GetMapping
    public String viewForm(Model m) {
        m.addAttribute("producto", new Producto());
        return PRODUCTO_FORMULARIO;
    }

    @PostMapping("/add")
    public String processForm(Model m, @Valid @ModelAttribute("producto") Producto producto, BindingResult result) {
        log.info("Producto procesado: " + producto);

        // Control de validación del formulario
        if (!result.hasErrors()) {
            // Si el producto es válido, lo añadimos a la lista
            productos.add(producto);
            m.addAttribute("productos", productos);  // Pasamos la lista de productos al modelo
            return PRODUCTO_INFO;
        } else {
            log.info("Hay errores: " + result.toString());
            return PRODUCTO_FORMULARIO;
        }
    }

    // Este método podría ser útil para mostrar todos los productos en la página de info de productos
    @GetMapping("/list")
    public String listProducts(Model m) {
        m.addAttribute("productos", productos);  // Pasamos la lista de productos al modelo
        return PRODUCTO_INFO;
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("cod") String cod, Model m) {
        // Buscar y eliminar el producto por su código
        productos.removeIf(producto -> producto.getCod().equals(cod));

        // Pasar la lista actualizada de productos al modelo
        m.addAttribute("productos", productos);

        // Redirigir a la página de productos después de la eliminación
        return "producto_info"; // Asegúrate de que esta es la vista correcta
    }

}
