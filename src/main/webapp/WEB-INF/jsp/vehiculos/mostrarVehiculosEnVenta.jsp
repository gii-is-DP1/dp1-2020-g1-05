<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="madaja" tagdir="/WEB-INF/tags" %>

<madaja:layout pageName="vehiculos">
	<h2>VehÝculos</h2>
	
	<a class="btn btn-default"  href='<spring:url value="/vehiculos/EnAlquiler"  ></spring:url>' >En Alquiler</a>
						
	<a class="btn btn-default" href='<spring:url value="/vehiculos/EnVenta" ></spring:url>'>En Venta</a>
	
	<a class="btn btn-default" href='<spring:url value="/vehiculos/Ambos" ></spring:url>'>Ambos</a> 
	
	<table id="ownersTable" class="table table-striped">
		<thead>
		<tr>
			<th style="width: 150px;">MatrÝcula</th>
			<th style="width: 150px;">Marca</th>
			<th style="width: 150px;">Modelo</th>
			<th style="width: 150px;">Precio de alquiler</th>
			<th style="width: 150px;">Precio de venta</th>
			<th style="width: 150px;">Plazas</th>
			<th style="width: 150px;">Puertas</th>
			<th style="width: 150px;">Disponibilidad</th>
			
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${vehiculosD}" var="vehiculo">
				<tr>
					<td>
						<spring:url value="/vehiculos/{vehiculoId}" var="vehiculoUrl">
							<spring:param name="vehiculoId" value="${vehiculo.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(vehiculoUrl)}"><c:out value="${vehiculo.matricula}"/></a>
					</td>
					<td>
						<c:out value="${vehiculo.marca}"/>
					</td>
					<td>
						<c:out value="${vehiculo.modelo}"/>
					</td>
					<td>
		           		<c:out value="${vehiculo.precioAlquiler}"/>
		           	</td>
					<td>
	           			<c:out value="${vehiculo.precioVenta}"/>
	           		</td>
					<td>
						<c:out value="${vehiculo.plazas}"/> pasajeros
					</td>
					<td>
						<c:out value="${vehiculo.puertas}"/> puertas
					</td>
					<td>
						<c:out value="${vehiculo.disponible}"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br/>
	<a class="btn btn-default" href='<spring:url value="/vehiculos" htmlEscape="true"/>'>Atras</a>
</madaja:layout>