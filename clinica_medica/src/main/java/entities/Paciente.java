package entities;

public class Paciente extends Persona{
	Integer id;
	String dni;
	String num_tel;
	Integer id_obra_social;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNum_tel() {
		return num_tel;
	}
	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}
	public Integer getId_obra_social() {
		return id_obra_social;
	}
	public void setId_obra_social(Integer id_obra_social) {
		this.id_obra_social = id_obra_social;
	}
		
	
}
