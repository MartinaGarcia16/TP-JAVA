package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataBase.DataAdministradores;
import dataBase.DataProfesionales;
import entities.Profesional;
import logic.ComunicacionDataAdm;
import logic.ComunicacionDataProf;

/**
 * Servlet implementation class ABMC_profesional
 */
@WebServlet("/ABMC_profesional")
public class ABMC_profesional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMC_profesional() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opc;
		LinkedList<Profesional> profesionales = new LinkedList<>();
		ComunicacionDataProf adm = new ComunicacionDataProf();
		opc = request.getParameter("opcion");
		
		if(opc.equals("nuevo_profesional")) {
			request.getRequestDispatcher("formularioNuevoProfesional.html").forward(request, response); 
		}
		
		
		if (opc.equals("alta_profesional")) {
			try {
				profesionales = adm.getAllADarDeAlta();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("profesionales", profesionales);
			request.getRequestDispatcher("WEB-INF/listadoProfesionalDarAlta.jsp").forward(request, response); 
		}
		
		
		if (opc.equals("baja_profesional")) {
			try {
				profesionales = adm.getAllDisponibles();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("profesionales", profesionales);
			request.getRequestDispatcher("WEB-INF/listadoProfesionalesDarBaja.jsp").forward(request, response); 
			
		}
		
		if (opc.equals("eliminar_profesional")) {
			try {
				profesionales = adm.getAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("profesionales", profesionales);
			request.getRequestDispatcher("WEB-INF/listadoProfesionalesEliminar.jsp").forward(request, response); 
		}
		
	}

		
		
		
		
	
	
	
	
	
	
	
	
}
