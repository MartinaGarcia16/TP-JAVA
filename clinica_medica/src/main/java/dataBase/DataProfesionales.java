package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Paciente;
import entities.Profesional;

public class DataProfesionales {
	public void altaProfesional(Integer mat) throws SQLException {
		PreparedStatement stmt=null;
		String consulta = "update profesionales set estado = 1 \r\n"
				+ "where matricula = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, mat);
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	} 
	
	public void bajaProfesional(Integer mat) throws SQLException {
		PreparedStatement stmt=null;
		String consulta = "update profesionales set estado = 0 \r\n"
				+ "where matricula = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, mat);
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
	} 
	
	
public LinkedList<Profesional> getAllADarDeAlta() throws SQLException {
		
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select matricula, nombre, apellido from profesionales \r\n"
							+ "where estado = 0");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Profesional p = new Profesional();
				p.setMatricula(rs.getString("matricula"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				profesionales.add(p);
					} // Fin del while
		} // Fin del if
		
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return profesionales;
	} // fin getAllADarDeAlta

public LinkedList<Profesional> getAllDisponibles() throws SQLException {
	
	Statement stmt=null;
	ResultSet rs=null;
	LinkedList<Profesional> profesionales = new LinkedList<>();
	try {
				
	// Ejecutar la query
	stmt = DbConnector.getInstancia().getConn().createStatement();
	rs = stmt.executeQuery("select matricula, nombre, apellido from profesionales \r\n"
						+ "where estado = 1");
				
	// Mapeao de ResultSet a objeto
	if (rs!=null) {
		while(rs.next()) {
			Profesional p = new Profesional();
			p.setMatricula(rs.getString("matricula"));
			p.setNombre(rs.getString("nombre"));
			p.setApellido(rs.getString("apellido"));
			profesionales.add(p);
				} // Fin del while
	} // Fin del if
	
	
	} catch(SQLException  ex) {
		ex.printStackTrace();
		
	} finally {
		try {
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			DbConnector.getInstancia().releaseConn();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	return profesionales;
} // fin getAll

	public void nuevoProfesional(Profesional p) {
			PreparedStatement stmt=null;
			//ResultSet rs=null;
			String consulta = "insert into profesionales \r\n"
								+ "(matricula, email, password, nombre, apellido, cod_especialidad, estado) \r\n"
								+ "values(?,?,?,?,?,?,?) \r\n";
			
			try {		
			// Ejecutar la query
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			stmt.setString(1, p.getMatricula());
			stmt.setString(2, p.getEmail());
			stmt.setString(3, p.getPassword());
			stmt.setString(4, p.getNombre());
			stmt.setString(5, p.getApellido());
			stmt.setInt(6, p.getCod_especialidad());
			stmt.setInt(7, p.getEstado());
			
			stmt.executeUpdate();

			} catch(SQLException  ex) {
				ex.printStackTrace();
				
			} finally {
				if(stmt!=null) {try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}			
				DbConnector.getInstancia().releaseConn();
			}
		}  // Fin altaPaciente
	
public LinkedList<Profesional> getAll() throws SQLException {
		
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select matricula, nombre, apellido from profesionales \r\n");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Profesional p = new Profesional();
				p.setMatricula(rs.getString("matricula"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				profesionales.add(p);
					} // Fin del while
		} // Fin del if
		
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return profesionales;
	} // fin getAll

	public void eliminarProfesional(String matricula) {
		PreparedStatement stmt=null;
		String consulta = "delete from profesionales where matricula = ?";
		
		try {
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, matricula);
		
		stmt.executeUpdate();
		
		} catch(SQLException  ex) {
			ex.printStackTrace();
			
		} finally {
			if(stmt!=null) {try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}			
			DbConnector.getInstancia().releaseConn();
		}
	} 
	
	public Profesional getProfesional(Profesional p) {
		Profesional prof = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select matricula from profesionales \r\n"
				+ "where matricula = ? or email = ?";
		
		try{
			// Crear la conexión
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setString(1, p.getMatricula());
			stmt.setString(2, p.getEmail());
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				prof = new Profesional(); 
				prof.setMatricula(rs.getString("matricula"));
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
			
		return prof;
	}
}

