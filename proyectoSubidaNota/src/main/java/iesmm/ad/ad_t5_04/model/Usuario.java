package iesmm.ad.ad_t5_04.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	public enum ROLE { administrador, tecnico, programador, alumno}

	private String nombreUsuario;
	private String nombreCompleto;
	private String email;
	private ROLE role;
	private List<Producto> productos;

	public Usuario() {
		this.productos = new ArrayList<>();
	}

	public Usuario(String nombreUsuario, String nombreCompleto, String email, ROLE role) {
		this.nombreUsuario = nombreUsuario;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.role = role;
		this.productos = new ArrayList<>();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public void addProducto(Producto producto) {
		this.productos.add(producto);
	}
}