package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataProfesionales;
import entities.Profesional;

public class ComunicacionDataProf {
	private DataProfesionales prof;
	
	public ComunicacionDataProf() {
		prof = new DataProfesionales();
	}
	
	public void altaProfesional(Integer mat) throws SQLException {
		prof.altaProfesional(mat);
	}
	
	public void bajaProfesional(Integer mat) throws SQLException {
		prof.bajaProfesional(mat);
	}
	
	public LinkedList<Profesional> getAllADarDeAlta() throws SQLException{
		return prof.getAllADarDeAlta();
	}
	
	public LinkedList<Profesional> getAllDisponibles() throws SQLException{
		return prof.getAllDisponibles();
	}
	
	public void nuevoProfesional(Profesional p) throws SQLException{
		prof.nuevoProfesional(p);
	}
	
	public LinkedList<Profesional> getAll() throws SQLException{
		return prof.getAll();
	}
	
	public void eliminarProfesional(String matricula) {
		prof.eliminarProfesional(matricula);
	}
	
	public Profesional validarExistencia(Profesional p) {
		return prof.getProfesional(p);
	}
	
}
