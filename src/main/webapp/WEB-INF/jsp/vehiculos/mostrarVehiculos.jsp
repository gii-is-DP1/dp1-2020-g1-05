<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="madaja" tagdir="/WEB-INF/tags" %>

<madaja:layout pageName="vehiculos">
	<h2>Veh�culos</h2>
	
	<table id="ownersTable" class="table table-striped">
		<thead>
		<tr>
			<th style="width: 150px;">Matr�cula</th>
			<th style="width: 150px;">Precio de alquiler</th>
			<th style="width: 150px;">Precio de venta</th>
			<th style="width: 150px;">Plazas</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${vehiculos}" var="vehiculo">
				<tr>
					<td>
						<spring:url value="/vehiculos/{vehiculoId}" var="vehiculoUrl">
							<spring:param name="vehiculoId" value="${vehiculo.id}"/>
						</spring:url>
						<a href="${fn:escapeXml(vehiculoUrl)}"><c:out value="${vehiculo.matricula}"/></a>
					</td>
					<td>
						<c:out value="${vehiculo.precioAlquiler}"/>
					</td>
					<td>
						<c:out value="${vehiculo.precioVenta}"/>
					</td>
					<td>
						<c:out value="${vehiculo.plazas}"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</madaja:layout>