package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Administrador;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;

public class DataAdministradores {
		
	

	public Administrador getAdministradorByUser(String email, String pass) throws SQLException {

	PreparedStatement stmt = null;
	ResultSet rs = null;
	Administrador a = null;
	String consulta = "select id_admin, nombre, apellido \r\n"
			+ " from administradores where email=? and password=?";
	try{
		// Crear la conexión
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		
		// Ejecutar la query
		stmt.setString(1, email);
		stmt.setString(2, pass);
		rs = stmt.executeQuery();
		
		// Mapeo de ResultSet a objeto
		if(rs!= null && rs.next()) {
			a = new Administrador(); 
			a.setId_admin(rs.getInt("id_admin"));
			a.setNombre(rs.getString("nombre"));
			a.setApellido(rs.getString("apellido"));
					} // Fin del if
		
		// Cerrar recursos
		if(stmt!=null) {stmt.close();}
		if(rs!=null) {rs.close();}
		DbConnector.getInstancia().releaseConn(); 
										
	} catch(SQLException  ex) {
		// Errores
		System.out.println("SQLException: "+ ex.getMessage());
		System.out.println("SQLState: "+ ex.getSQLState());
		System.out.println("VendorError: "+ ex.getErrorCode());
	}
	return a;
} // Fin del getById

}
