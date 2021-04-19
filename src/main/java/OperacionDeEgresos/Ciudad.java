package OperacionDeEgresos;

import Otros.PersistedEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ciudad extends PersistedEntity {
    String idCiudad;
    String nombre;

    public Ciudad() {}

    public Ciudad(String nombre, String idCiudad) {
        this.nombre = nombre;
        this.idCiudad= idCiudad;
    }
}
