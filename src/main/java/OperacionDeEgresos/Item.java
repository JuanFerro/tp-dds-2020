package OperacionDeEgresos;

import Otros.PersistedEntity;

import javax.persistence.Entity;

@Entity
public class Item extends PersistedEntity {

	private String nombre;
	private String descrpicion;
	private double valor;

	public Item () {}

	public Item(String nombre, String descripcion, double valor) {
		this.nombre = nombre;
		this.descrpicion = descripcion;
		this.valor = valor;
	}

	public String getNombre() { return nombre; }
	public String getDescripcion() { return descrpicion; }
	public double getValor() { return valor; }

}
