package Organizaciones.Reglas;

import Organizaciones.Entidad;

import javax.persistence.Entity;

@Entity
public class ReglaBloqueante extends ReglaDeNegocio {

    public ReglaBloqueante() {}

    public ReglaBloqueante(TipoDeRegla tipo) {
        super(tipo);
    }

    @Override
    public void permite(Entidad entidad) {
        throw new ReglaDeNegocioException("La regla no te permite hacer esta operacion");
    }

}

