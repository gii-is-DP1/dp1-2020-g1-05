package com.springframework.samples.madaja.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;


@Entity
@Table(name = "gestores")
public class Gestor extends Trabajador{
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "concesionario", referencedColumnName = "gestor")
	private Concesionario concesionario;

	public Concesionario getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(Concesionario concesionario) {
		this.concesionario = concesionario;
	}

	@Override
	public String toString() {
		ToStringCreator builder = new ToStringCreator(this);
		builder.append("concesionario", concesionario);
		return builder.toString();
	}
	
	
}