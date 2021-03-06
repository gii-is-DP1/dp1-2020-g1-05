package com.springframework.samples.madaja.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.search.annotations.*;
import org.hibernate.search.bridge.builtin.IntegerBridge;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vehiculos")
@Indexed
public class Vehiculos extends BaseEntity {

	@Column(name = "matricula")
	@NotEmpty
	private String matricula;

	@Column(name = "precio_alquiler")
	@Positive
	private Integer precioAlquiler;

	@Column(name = "precio_venta")
	@Positive
	private Integer precioVenta;

	@Column(name = "marca")
	@NotEmpty
	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	private String marca;

	@Column(name = "modelo")
	@NotEmpty
	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	private String modelo;

	@Column(name = "puertas")
	@Positive
	@Field
	@FieldBridge(impl = IntegerBridge.class)
	private Integer puertas;

	@Column(name = "plazas")
	@Positive
	@Field
	@FieldBridge(impl = IntegerBridge.class)
	private Integer plazas;

	@ManyToOne
	@JoinColumn(name = "cambio_id")
	@JsonIgnore
	private Cambio cambio;

	@Column(name = "maletero")
	@PositiveOrZero
	private Integer maletero;

	@Column(name = "km_actuales")
	@PositiveOrZero
	private Integer kmActuales;

	@Column(name = "caracteristicas")
	@NotEmpty
	private String caracteristicas;

	@Column(name = "estado")
	@NotEmpty
	private String estado;

	@ManyToOne
	@JoinColumn(name = "disponible_id")
	private Disponible disponible;

	@ManyToOne
	@JoinColumn(name = "combustible_id")
	@JsonIgnore
	private Combustible combustible;

	@ManyToOne
	@JoinColumn(nullable = true)
	@JsonIgnore
	private Concesionario concesionario;

	@OneToOne
	@JoinColumn(name = "seguro_vehiculo_id", unique = true, nullable = true)
	@JsonIgnore
	private SeguroVehiculo seguroVehiculo;

	@OneToMany(mappedBy = "vehiculos", cascade = CascadeType.ALL)
	private Set<Incidencia> incidencias;

	// ANTONIO
	@OneToMany(mappedBy = "vehiculos", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<SeguroCliente> segurosCliente;
	//

	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Venta> ventas;

	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Alquiler> alquileres;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = true)
	private Oferta oferta;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getPrecioAlquiler() {
		return precioAlquiler;
	}

	public void setPrecioAlquiler(Integer precioAlquiler) {
		this.precioAlquiler = precioAlquiler;
	}

	public Integer getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getPuertas() {
		return puertas;
	}

	public void setPuertas(Integer puertas) {
		this.puertas = puertas;
	}

	public Integer getPlazas() {
		return plazas;
	}

	public void setPlazas(Integer plazas) {
		this.plazas = plazas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Integer getMaletero() {
		return maletero;
	}

	public void setMaletero(Integer maletero) {
		this.maletero = maletero;
	}

	public Integer getKmActuales() {
		return kmActuales;
	}

	public void setKmActuales(Integer kmActuales) {
		this.kmActuales = kmActuales;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Disponible getDisponible() {
		return disponible;
	}

	public void setDisponible(Disponible disponible) {
		this.disponible = disponible;
	}

	public Combustible getCombustible() {
		return combustible;
	}

	public void setCombustible(Combustible combustible) {
		this.combustible = combustible;
	}

	public Concesionario getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public SeguroVehiculo getSeguroVehiculo() {
		return seguroVehiculo;
	}

	public void setSeguroVehiculo(SeguroVehiculo seguroVehiculo) {
		this.seguroVehiculo = seguroVehiculo;
	}

	protected Set<Incidencia> getIncidenciasInternal() {
		if (this.incidencias == null) {
			this.incidencias = new HashSet<>();
		}
		return this.incidencias;
	}

	protected void setIncidenciasInternal(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public List<Incidencia> getIncidencias() {
		List<Incidencia> sortedIncidencias = new ArrayList<>(getIncidenciasInternal());
		PropertyComparator.sort(sortedIncidencias, new MutableSortDefinition("descripcion", true, true));
		return Collections.unmodifiableList(sortedIncidencias);
	}

	public void addIncidencia(Incidencia incidencia) {
		getIncidenciasInternal().add(incidencia);
		incidencia.setVehiculos(this);
	}

	public boolean removeIncidencia(Incidencia incidencia) {
		return getIncidenciasInternal().remove(incidencia);
	}

	protected Set<SeguroCliente> getSegurosClienteInternal() {
		if (this.segurosCliente == null) {
			this.segurosCliente = new HashSet<>();
		}
		return this.segurosCliente;
	}

	protected void setSegurosClienteInternal(Set<SeguroCliente> segurosCliente) {
		this.segurosCliente = segurosCliente;
	}

	public List<SeguroCliente> getSegurosCliente() {
		List<SeguroCliente> sortedSegurosCliente = new ArrayList<>(getSegurosClienteInternal());
		PropertyComparator.sort(sortedSegurosCliente, new MutableSortDefinition("descripcion", true, true));
		return Collections.unmodifiableList(sortedSegurosCliente);
	}

	public void addSeguroCliente(SeguroCliente seguroCliente) {
		getSegurosClienteInternal().add(seguroCliente);
		seguroCliente.setVehiculos(this);
	}

	public boolean removeSeguroCliente(SeguroCliente seguroCliente) {
		return getSegurosClienteInternal().remove(seguroCliente);
	}

	protected Set<Venta> getVentasInternal() {
		if (this.ventas == null) {
			this.ventas = new HashSet<>();
		}
		return this.ventas;
	}

	protected void setVentasInternal(Set<Venta> ventas) {
		this.ventas = ventas;
	}

	public List<Venta> getVentas() {
		List<Venta> sortedVentas = new ArrayList<>(getVentasInternal());
		PropertyComparator.sort(sortedVentas, new MutableSortDefinition("id", true, true));
		return Collections.unmodifiableList(sortedVentas);
	}

	public void addVenta(Venta venta) {
		getVentasInternal().add(venta);
		venta.setVehiculo(this);
	}

	public boolean removeVenta(Venta venta) {
		return getVentasInternal().remove(venta);
	}

	protected Set<Alquiler> getAlquileresInternal() {
		if (this.alquileres == null) {
			this.alquileres = new HashSet<>();
		}
		return this.alquileres;
	}

	protected void setAlquileresInternal(Set<Alquiler> alquiler) {
		this.alquileres = alquiler;
	}

	public List<Alquiler> getAlquileres() {
		List<Alquiler> sortedAlquileres = new ArrayList<>(getAlquileresInternal());
		PropertyComparator.sort(sortedAlquileres, new MutableSortDefinition("id", true, true));
		return Collections.unmodifiableList(sortedAlquileres);
	}

	public void addAlquiler(Alquiler alquiler) {
		getAlquileresInternal().add(alquiler);
		alquiler.setVehiculo(this);
	}

	public boolean removeAlquiler(Alquiler alquiler) {
		return getAlquileresInternal().remove(alquiler);
	}

}
