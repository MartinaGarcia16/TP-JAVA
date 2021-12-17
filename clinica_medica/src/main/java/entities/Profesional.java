package entities;

public class Profesional extends Persona{
	String matricula;
	Integer cod_especialidad;
	Integer estado;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Integer getCod_especialidad() {
		return cod_especialidad;
	}
	public void setCod_especialidad(Integer cod_especialidad) {
		this.cod_especialidad = cod_especialidad;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
}
