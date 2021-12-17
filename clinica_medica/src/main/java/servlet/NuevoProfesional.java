package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Profesional;
import logic.ComunicacionDataProf;

/**
 * Servlet implementation class NuevoProfesional
 */
@WebServlet("/NuevoProfesional")
public class NuevoProfesional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NuevoProfesional() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Profesional p = new Profesional();
		Profesional p2 = new Profesional();
		ComunicacionDataProf adm = new ComunicacionDataProf();
		PrintWriter out = response.getWriter();
		
		Integer op = Integer.parseInt(request.getParameter("opc"));
		p.setMatricula(request.getParameter("matricula"));
		p.setEmail(request.getParameter("email"));
		p2 = adm.validarExistencia(p);
		
		if (op == 1) {
			if (p2 == null) {
				p.setPassword(request.getParameter("password"));
				p.setNombre(request.getParameter("nombre"));
				p.setApellido(request.getParameter("apellido"));
				p.setCod_especialidad(Integer.parseInt(request.getParameter("codigo")));
				p.setEstado(1);
				try {
					adm.nuevoProfesional(p);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("nuevoProfesionalExito.html").forward(request, response); 
			}
		
			else {
				out.print("Sorry professional already exists! Try again - "); 
				request.getRequestDispatcher("formularioNuevoProfesional.html").forward(request, response);
			}
		}
		else {request.getRequestDispatcher("menuAdministrador.html").forward(request, response);}
	}
}
