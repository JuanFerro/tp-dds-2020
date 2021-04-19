package OperacionDeEgresos;

import Otros.PersistedEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class MedioDePago extends PersistedEntity {

    public MedioDePago() {

    }

    private int identificacion;

    @Enumerated
    private TipoMedioDePago tipo;

    public MedioDePago (int identificacion, TipoMedioDePago tipo){
        this.identificacion = identificacion;
        this.tipo = tipo;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public TipoMedioDePago getTipo() {
        return tipo;
    }
}



