package logic;

import java.sql.SQLException;
import java.util.LinkedList;

import dataBase.DataAdministradores;
import dataBase.FuncionesDb;
import entities.Administrador;
import entities.Profesional;

public class ComunicacionDataAdm {
	private DataAdministradores adm;
	
	public ComunicacionDataAdm(){
		adm = new DataAdministradores();
	}
		
	public Administrador getAdministradorByUser(String a, String b) throws SQLException {
		return adm.getAdministradorByUser(a, b);
	}
	
}
