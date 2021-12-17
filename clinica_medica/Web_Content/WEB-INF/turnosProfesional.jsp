<%@page import="java.util.LinkedList"%>
<%@page import="entities.Turnos"%>
<%@page import="entities.Profesional"%>
<%@page import="entities.Paciente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listado turnos</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/estilos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<body>
	<%
		LinkedList<Turnos> turnos = (LinkedList<Turnos>)request.getAttribute("turnosProfesional");
		LinkedList<Paciente> pacientes = (LinkedList<Paciente>)request.getAttribute("pacientes");
		Profesional prof = (Profesional)request.getAttribute("profesional");
	%>
<form action="#" method="post">

		<h2>Profesinal: <%=prof.getNombre() %> <%=prof.getApellido() %></h2>
		<div class="container">
 			<table class="table table-striped">
    			<thead>
      				<tr>
        				<th>Fecha turno</th>
        				<th>Hora turno</th>
        				<th>Nombre paciente</th>
        				<th>Apellido paciente</th>
     				 </tr>
    			</thead>
    
    			<tbody>
    
   					 <% for (Turnos t:turnos) { %> 

      				<tr>
        				<td><%=t.getFecha_turno() %></td>
        				<td><%=t.getHora_turno() %></td>        				
      				</tr>
    				 <% } %>
   				</tbody> 
  			</table>
  			<button class="btn btn-success" name="nro_turno" type="submit" value="0">Volver atrás</button>
		</div>
</form>
</body>

</html>