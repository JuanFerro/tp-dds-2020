package OperacionDeEgresos;

import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Provincia extends PersistedEntity {
    String idProvincia;
    String nombre;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "provincia_id")
    private List<Ciudad> ciudades;

    public Provincia() {}

    public Provincia(String nombre, List<Ciudad> ciudades, String idProvincia) {
        this.nombre = nombre;
        this.ciudades=ciudades;
        this.idProvincia=idProvincia;
    }
}
