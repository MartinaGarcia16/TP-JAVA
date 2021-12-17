package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Administrador;
import entities.Paciente;
import entities.Persona;
import entities.Profesional;
import logic.ComunicacionDataAdm;
import logic.ComunicacionDb;

@WebServlet({ "/Signin", "/signin", "/SignIn", "/SIGNIN", "/signIn" })
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Signin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Paciente pac = new Paciente();
		Administrador admin = new Administrador();
		ComunicacionDataAdm comAdmin = new ComunicacionDataAdm();
		ComunicacionDb ctrl = new ComunicacionDb();
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			pac = ctrl.validateLogin(email, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			admin = comAdmin.getAdministradorByUser(email, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if (pac != null){
		request.getSession().setAttribute("usuario", pac);
		request.getRequestDispatcher("menu.html").forward(request, response); 
				}
		
		else if (admin != null){
			request.getSession().setAttribute("administrador", admin);
			request.getRequestDispatcher("menuAdministrador.html").forward(request, response); 
					} 
		
		else {
			out.print("Sorry Email or Password Error! Try again - "); 
			RequestDispatcher rd = request.getRequestDispatcher("index2.html");
			rd.include(request, response); 
				} 
		}	
}


