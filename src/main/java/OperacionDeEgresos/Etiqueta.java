package OperacionDeEgresos;

import Otros.PersistedEntity;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Etiqueta extends PersistedEntity {
    String nombre;

    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }

    public void nombre(String nuevoNombre) { nombre = nuevoNombre; }

}
