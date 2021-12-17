package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ComunicacionDataProf;

/**
 * Servlet implementation class AltaProfesional
 */
@WebServlet("/AltaProfesional")
public class AltaProfesional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaProfesional() {
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
		
		ComunicacionDataProf com = new ComunicacionDataProf();
		int matricula = Integer.parseInt(request.getParameter("nro_matricula"));
		try {
			com.altaProfesional(matricula);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("menuAdministrador.html").forward(request, response); 
	}

}
