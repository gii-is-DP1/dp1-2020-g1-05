package com.springframework.samples.madaja.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recogida")
public class Recogida extends Localizacion {

	@Column(name = "fecha")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fecha;

	@Column(name = "hora")
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime hora;

	@OneToOne(mappedBy = "recogida", optional = true)
	@JsonIgnore
	private Alquiler alquiler;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Mecanico mecanico;

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Alquiler getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	@Override
	public String toString() {
		ToStringCreator builder = new ToStringCreator(this);
		builder.append("hora", hora);
		builder.append("alquiler", alquiler);
		builder.append("mecanico", mecanico);
		return builder.toString();
	}

}
