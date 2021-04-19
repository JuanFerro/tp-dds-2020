package OperacionDeEgresos;

import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pais extends PersistedEntity {
    String idPais;
    String nombre;
    String moneda;

    @OneToMany(fetch = FetchType.LAZY)  // Para que no traiga todas las provincias si no se necesitan en ese momento
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "pais_id")
    private List<Provincia> provincias;

    public Pais() {}

    public Pais(String nombre, String moneda, List<Provincia> provincias, String idPais) {
        this.nombre = nombre;
        this.moneda = moneda;
        this.provincias = provincias;
        this.idPais = idPais;
    }

    public String monedaDelPais(){return moneda;}
}