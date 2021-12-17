package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ComunicacionDataProf;

/**
 * Servlet implementation class EliminarProfesional
 */
@WebServlet("/EliminarProfesional")
public class EliminarProfesional extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarProfesional() {
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
		ComunicacionDataProf adm = new ComunicacionDataProf();
		String opc = request.getParameter("nro_matricula");
		
		if (Integer.parseInt(request.getParameter("nro_matricula")) != 0) {
			adm.eliminarProfesional(opc);
			request.getRequestDispatcher("menuAdministrador.html").forward(request, response);
		}
		
		else {request.getRequestDispatcher("menuAdministrador.html").forward(request, response);}
	}
}