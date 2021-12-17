package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Especialidad;
import entities.Paciente;
import entities.Profesional;
import entities.Turnos;
import logic.ComunicacionDb;

@WebServlet({ "/ReservarTurno", "/reservarTurno", "/reservarturno" })
public class ReservarTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReservarTurno() {
        super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath()); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LinkedList<Profesional> profesionales = new LinkedList<>();
		LinkedList<Turnos> turnosPacienteActual = new LinkedList<>();
		Paciente p = new Paciente();
		Especialidad esp = new Especialidad();
		ComunicacionDb ctrl = new ComunicacionDb();
		LinkedList<Especialidad> especialidades = new LinkedList<>();
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Boolean bandera = true;
		
		String op = request.getParameter("codigo_esp");
		int cod = Integer.parseInt(op);
		
		if (cod != 0) {
		esp.setCodigo_esp(cod);
		
		try {
			profesionales = ctrl.getByEspecialidad(esp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		p = (Paciente) session.getAttribute("usuario");
		try {
			turnosPacienteActual = ctrl.getTurnosPaciente(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (turnosPacienteActual != null) {
			for (Turnos t:turnosPacienteActual) {
			
				Profesional prof = new Profesional();
				prof.setMatricula(t.getMatricula_prof());
	
				try {
					prof = ctrl.getProfesional(prof);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (prof.getCod_especialidad() == cod) { bandera = false; }
			}
		}	
		
		if (bandera == true || turnosPacienteActual == null) {
				request.setAttribute("elegirProfesionales", profesionales);
				request.getRequestDispatcher("WEB-INF/elegirProfesional.jsp").forward(request, response); 
		}
			else {
				Boolean aviso = true;
				especialidades = ctrl.getAllEspecialidades();
				request.setAttribute("especialidades", especialidades);
				request.setAttribute("aviso", aviso);
				request.getRequestDispatcher("WEB-INF/elegirEspecialidad.jsp").forward(request, response);
				out.print("El usuario ya tiene un turno pendiente para la especialidad seleccionada");	
		}
		} // fin del primer if 
		
		else {
			request.getRequestDispatcher("menu.html").forward(request, response); 
		}
	}

}
