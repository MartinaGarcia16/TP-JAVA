package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.ObraSocial;
import entities.Paciente;
import logic.ComunicacionDb;


@WebServlet({ "/CrearCuenta", "/crearcuenta", "/Crearcuenta", "/crearCuenta" })
public class CrearCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CrearCuenta() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Paciente pac = new Paciente();
		ObraSocial obraSocial = new ObraSocial();
;		Boolean encontrado = false;
		ComunicacionDb ctrl = new ComunicacionDb();
		PrintWriter out = response.getWriter();
		
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String dni = request.getParameter("dni");
		String cel = request.getParameter("nro");
		String os = request.getParameter("obra_social");
		String email = request.getParameter("email");   
		String password = request.getParameter("password");
		String op = request.getParameter("obra_social");
		int id_os = Integer.parseInt(op);
		

		try {
			encontrado = ctrl.validateCreateAccount(email);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if (encontrado == false) {
		
			try {
				obraSocial = ctrl.getObraSocialByNombre(id_os);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			pac.setEmail(email);
			pac.setPassword(password);
			pac.setNombre(nombre);
			pac.setApellido(apellido);
			pac.setDni(dni);
			pac.setNum_tel(cel);
			pac.setId_obra_social(obraSocial.getId_obra_social());
			
			try {
				ctrl.altaPaciente(pac);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("bienvenido.html").forward(request, response); 
			}	
			
		else {
			out.print("El usuario ingresado ya existe"); 
			RequestDispatcher rd = request.getRequestDispatcher("crearCuenta.html");
			rd.include(request, response); 
		}
					
	}
}

