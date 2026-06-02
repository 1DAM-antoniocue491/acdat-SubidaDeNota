package iesmm.ad.ad_t5_04.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Producto {
    @Pattern(regexp = "^[a-zA-Z][0-9]+", message = "Debe contener una letra seguida de números")
    private String cod;

    @Size(min = 3, max = 20, message = "El nombre debe tener mas de 3 letras y menos de 20.")
    private String nombre;

    @NotNull(message = "Debe indicar descripción del producto")
    private String descripcion;

    @NotNull(message = "Debes concretar fabricante")
    private String fabricante;

    @NotNull(message = "Debes especificar precio")
    @Min(value = 0, message = "El precio mínimo es 0")
    private Float precio;

    @NotNull
    @Min(value = 0, message = "debe haber alguna unidad")
    private Integer unidades;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return "(" + this.getCod() + ", " + this.getNombre() + ")";
    }
}
