<%@page import="java.util.LinkedList"%>
<%@page import="entities.Profesional"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Eliminar profesional</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/estilos.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<body>
	<%
		LinkedList<Profesional> profesionales = (LinkedList<Profesional>)request.getAttribute("profesionales");
	%>
 
	<form action="EliminarProfesional" method="post">


		<div class="container">
      
 			<table class="table table-striped">
    			<thead>
      				<tr>
        				<th>Matricula</th>
        				<th>Nombre</th>
        				<th>Apellido</th>
        				<th>Seleccione un profesional</th>
     				 </tr>
    			</thead>
    
    			<tbody>
   					 <% for (Profesional p:profesionales) { %> 
   				
      				<tr>
      					<td><%=p.getMatricula() %></td>
        				<td><%=p.getNombre() %></td>
        				<td><%=p.getApellido() %></td>
        				<td><button class="btn btn-danger" name="nro_matricula" type="submit" value="<%=p.getMatricula() %>">Eliminar</button></td> 
      				</tr>
    				 <% } %>
   				</tbody> 
  			</table>
  			<button class="btn btn-success" name="nro_matricula" type="submit" value="0">Volver atras</button>
		</div>
	</form>

</body>

</html>