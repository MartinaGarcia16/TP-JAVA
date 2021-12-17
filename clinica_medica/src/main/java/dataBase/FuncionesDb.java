package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.Especialidad;
import entities.Especialidad_ObralSocial;
import entities.ObraSocial;
import entities.Paciente;
import entities.Persona;
import entities.Profesional;
import entities.Turnos;
import entities.Valor_especialidad;

public class FuncionesDb {
	public Paciente getByUser(String email, String pass) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Paciente p = null;
		String consulta = "select id, nombre, apellido, email \r\n"
				+ " from pacientes where email=? and password=?";

		
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);

			// Ejecutar la query
			stmt.setString(1, email);
			stmt.setString(2, pass);
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) {
				p = new Paciente(); 
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setEmail(rs.getString("email"));
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
		return p;
} // Fin del getById
	
	public LinkedList<Paciente> getAll() throws SQLException {
		
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Paciente> pacientes = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select * from pacientes");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Paciente p = new Paciente();
				p.setId(rs.getInt("id"));
				p.setDni(rs.getString("dni"));
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setNum_tel(rs.getString("num_tel"));
				p.setEmail(rs.getString("email"));
				pacientes.add(p);
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
		
		return pacientes;
	} // fin getAll
	
	public LinkedList<Profesional> getByEspecialidad(Especialidad e) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		String consulta = "select prof.nombre, prof.apellido, prof.matricula \r\n"
				+ "from profesionales prof \r\n"
				+ "where prof.cod_especialidad =? and prof.estado=1";
		
		try {		
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, e.getCodigo_esp());
		rs = stmt.executeQuery();
		
		// Mapeo de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Profesional prof = new Profesional();
				prof.setNombre(rs.getString("nombre"));
				prof.setApellido(rs.getString("apellido"));
				prof.setMatricula(rs.getString("matricula"));
				profesionales.add(prof);
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
	} // Fin getByEspecialidad
	
	public Especialidad getEspecialidadByCodigo(Especialidad e) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select e.nombre from especialidades e where e.codigo_esp =?";

		try {		
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, e.getCodigo_esp());
		rs = stmt.executeQuery();
		
		// Mapeo de ResultSet a objeto
		while(rs.next()) {
			if (rs!=null) {		
				e.setNombre(rs.getString("nombre"));
						} // Fin del if
		}
		
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

		return e;
	} // Fin getEspecialidadByCodigo
	
	public void altaPaciente(Paciente p) {
		PreparedStatement stmt=null;
		//ResultSet rs=null;
		String consulta = "insert into pacientes \r\n"
							+ "(nombre, apellido, dni, email, password, num_tel, id_obra_social) \r\n"
							+ "values(?,?,?,?,?,?,?) \r\n" 
							+ PreparedStatement.RETURN_GENERATED_KEYS;
		
		try {		
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getNombre());
		stmt.setString(2, p.getApellido());
		stmt.setString(3, p.getDni());
		stmt.setString(4, p.getEmail());
		stmt.setString(5, p.getPassword());
		stmt.setString(6, p.getNum_tel());
		stmt.setInt(7, p.getId_obra_social());
		
		stmt.executeUpdate();
		ResultSet Keyrs = stmt.getGeneratedKeys(); 
		
		if (Keyrs != null && Keyrs.next()) {
			int id = Keyrs.getInt(1);
			p.setId(id);		
		}		
		
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
	
	public LinkedList<Turnos> getTurnos(Profesional p) throws SQLException {
		LinkedList<Turnos> turnos = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select t.fecha_turno, t.hora_turno, t.numero \r\n"
				+ "from turnos t \r\n"
				+ "inner join profesionales p \r\n"
				+ "	on p.matricula = t.matricula_prof \r\n"
				+ "where p.matricula = ? and t.id_paciente is null \r\n"
				+ "order by t.fecha_turno, t.hora_turno";
			
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Turnos t = new Turnos();
				t.setFecha_turno(rs.getDate("fecha_turno"));
				t.setHora_turno(rs.getTime("hora_turno"));
				t.setNumero(rs.getInt("numero"));
				turnos.add(t);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return turnos;
	} // Fin getTurnos
	
	public Profesional getProfesional(Profesional p) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select p.nombre, p.apellido, p.cod_especialidad from profesionales p "
						+	 "where p.matricula = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
				p.setNombre(rs.getString("nombre"));
				p.setApellido(rs.getString("apellido"));
				p.setCod_especialidad(rs.getInt("cod_especialidad"));
					} // Fin if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return p;
	} //fin getProfesional
	
	public void asignarTurno(Turnos t, Paciente p) throws SQLException {
		PreparedStatement stmt=null;
		String consulta = "update turnos set id_paciente = ? \r\n"
				+ "where numero = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, p.getId());
		stmt.setInt(2, t.getNumero());
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	} //fin asignarTurno
		
	public Valor_especialidad getValorEspecialidad(Profesional p) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Valor_especialidad valor_esp = new Valor_especialidad(); 
		String consulta = "select max(ve.valor) valor \r\n"
				+ "from valor_especialidad ve \r\n"
				+ "inner join especialidades e \r\n"
				+ "	on e.codigo_esp = ve.cod_especialidad \r\n"
				+ "where e.codigo_esp = ?";	
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, p.getCod_especialidad());
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
				valor_esp.setValor(rs.getInt("valor"));
					} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return valor_esp;
		
	} // fin getValorEspecialidad
	
	
	public Turnos getTurno(Turnos t) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Turnos turno = new Turnos();
		String consulta = "select t.numero, t.fecha_turno, t.hora_turno, t.matricula_prof from turnos t \r\n"
				+ "where t.numero = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, t.getNumero());
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
			turno.setNumero(rs.getInt("numero"));
			turno.setFecha_turno(rs.getDate("fecha_turno"));
			turno.setHora_turno(rs.getTime("hora_turno"));
			turno.setMatricula_prof(rs.getString("matricula_prof"));
			
		} //fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return turno;
	}
	
	public ObraSocial getObraSocial(Paciente p) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ObraSocial os = new ObraSocial();
		String consulta = "select os.nombre, os.id_obra_social from obras_sociales os \r\n"
				+ "inner join pacientes p \r\n"
				+ "	on os.id_obra_social = p.id_obra_social \r\n"
				+ "where p.id_obra_social = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, p.getId_obra_social());
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
			os.setNombre(rs.getString("nombre"));
			os.setId_obra_social(rs.getInt("id_obra_social"));
		} //fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return os;
	}
	
	public Especialidad_ObralSocial getPorcentajeCobertura(Integer e, Integer os) throws SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Especialidad_ObralSocial esp_os = new Especialidad_ObralSocial();
		String consulta = "select e_os.porcentaje_cobertura from especialidad_obrasocial e_os \r\n"
				+ "where e_os.cod_especialidad = ? and e_os.id_os = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, e);
		stmt.setInt(2, os);
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
			esp_os.setProcentaje_cobertura(rs.getFloat("porcentaje_cobertura"));
		} //fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return esp_os;
	}
	
	public LinkedList<Turnos> getTurnosPaciente(Paciente p) throws SQLException{
		LinkedList<Turnos> turnosPaciente = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select t.numero, t.fecha_turno, t.hora_turno, t.matricula_prof, t.id_paciente \r\n"
				+ "from turnos t \r\n"
				+ "inner join pacientes p \r\n"
				+ "	on t.id_paciente = p.id where p.id = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, p.getId());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Turnos t = new Turnos();
				t.setNumero(rs.getInt("numero"));
				t.setFecha_turno(rs.getDate("fecha_turno"));
				t.setHora_turno(rs.getTime("hora_turno"));
				t.setMatricula_prof(rs.getString("matricula_prof"));
				turnosPaciente.add(t);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return turnosPaciente;
	}
	
	public void cancelarTurno(Integer nro_turno) throws SQLException {
		PreparedStatement stmt=null;
		String consulta = "update turnos set id_paciente = null \r\n"
				+ "where numero = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, nro_turno);
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	} //fin cancelarTurno
	
	public Profesional getProfesionalByUser(Profesional p) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Profesional prof = new Profesional();
		String consulta = "select matricula, nombre, apellido, email, cod_especialidad \r\n"
				+ " from profesionales where email=? and password=?";
		try{
			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, p.getEmail());
			stmt.setString(2, p.getPassword());
			rs = stmt.executeQuery();
			
			// Mapeo de ResultSet a objeto
			if(rs!= null && rs.next()) { 
				prof.setMatricula(rs.getString("matricula"));
				prof.setNombre(rs.getString("nombre"));
				prof.setApellido(rs.getString("apellido"));
				prof.setEmail(rs.getString("email"));
				prof.setCod_especialidad(rs.getInt("cod_especialidad"));
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
} // Fin del getProfesionalById
	
	public LinkedList<Turnos> getTurnosProfesional(Profesional p) throws SQLException{
		LinkedList<Turnos> turnosProfesional = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select  t.fecha_turno, t.hora_turno  from turnos t \r\n"
							+ "where t.matricula_prof = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Turnos t = new Turnos();
				t.setFecha_turno(rs.getDate("fecha_turno"));
				t.setHora_turno(rs.getTime("hora_turno"));
				turnosProfesional.add(t);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return turnosProfesional;
	}
	
	public LinkedList<Paciente> getTurnosPacientesProfActual(Profesional p) throws SQLException{
		LinkedList<Paciente> pacientes = new LinkedList<>();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String consulta = "select p.nombre, p.apellido \r\n"
				+ "from pacientes p \r\n"
				+ "inner join turnos t \r\n"
				+ "	on p.id = t.id_paciente \r\n"
				+ "where t.matricula_prof = ?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getMatricula());
		rs = stmt.executeQuery();
		
		if (rs!=null) {
			while(rs.next()) {
				Paciente pac = new Paciente();
				pac.setNombre(rs.getString("nombre"));
				pac.setApellido(rs.getString("apellido"));
				pacientes.add(pac);
					} // Fin del while
		} // Fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
		return pacientes;
	}

	public void actualizarDatosPaciente(Paciente p) throws SQLException {
		PreparedStatement stmt=null;
		String consulta = "update pacientes set email=?, password=?, num_tel=? \r\n"
				+ "where id=?";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setString(1, p.getEmail());
		stmt.setString(2, p.getPassword());
		stmt.setString(3, p.getNum_tel());
		stmt.setInt(4, p.getId());
		stmt.executeUpdate();
		
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();
		
	} //fin cancelarTurno
	
	public ObraSocial getObraSocialByNombre (Integer id_os) throws SQLException {
		ObraSocial os = new ObraSocial();
		PreparedStatement stmt=null;
		ResultSet rs = null;
		String consulta = "select id_obra_social from obras_sociales \r\n"
						+ "where id_obra_social = ? ";
		
		stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
		stmt.setInt(1, id_os);
		rs = stmt.executeQuery();
		
		if(rs!= null && rs.next()) {
			os.setId_obra_social(rs.getInt("id_obra_social"));
		} //fin del if
		
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
		DbConnector.getInstancia().releaseConn();

		return os;
		
	}
	
	public Boolean validateCreateAccount(String email) throws SQLException {
		Boolean encontrado = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Paciente pac = null;
		String consulta = "select email \r\n"
				+ " from pacientes where email=?";

			// Crear la conexi�n
			stmt = DbConnector.getInstancia().getConn().prepareStatement(consulta);
			
			// Ejecutar la query
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			
			if(rs.next()) {encontrado = true;}
			
			if(stmt!=null) {stmt.close();}
			if(rs!=null) {rs.close();}
			DbConnector.getInstancia().releaseConn(); 
				
		return encontrado;
	}
	
	public LinkedList<Especialidad> getAllEspecialidades(){
		LinkedList<Especialidad> especialidades = new LinkedList<>();
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Paciente> pacientes = new LinkedList<>();
		try {
					
		// Ejecutar la query
		stmt = DbConnector.getInstancia().getConn().createStatement();
		rs = stmt.executeQuery("select * from especialidades");
					
		// Mapeao de ResultSet a objeto
		if (rs!=null) {
			while(rs.next()) {
				Especialidad e  = new Especialidad();
				e.setCodigo_esp(rs.getInt("codigo_esp"));
				e.setNombre(rs.getString("nombre"));
				especialidades.add(e);
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
		
		return especialidades;
	} // fin getAllEspecialidades
	
}
