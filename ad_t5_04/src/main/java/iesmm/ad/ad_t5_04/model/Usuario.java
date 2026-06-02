package iesmm.ad.ad_t5_04.model;

public class Usuario {
	public enum ROLE { administrador, tecnico, programador}

	private String nombreUsuario;
	private String nombreCompleto;
	private String email;
	private ROLE role;
	
	public Usuario() {}
	
	public Usuario(String nombreUsuario, String nombreCompleto, String email, ROLE role) {
		this.nombreUsuario = nombreUsuario;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.role = role;
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
}