package Organizaciones.Reglas;

import Organizaciones.Entidad;

import javax.persistence.Entity;


@Entity
public class ReglaBloquearSiSuperoMonto extends ReglaDeNegocio {

    int montoMaximo;

    public ReglaBloquearSiSuperoMonto() {}

    public ReglaBloquearSiSuperoMonto(int montoMaximo) {
        super(TipoDeRegla.aceptarNuevoEgreso);
        this.montoMaximo = montoMaximo;
    }

    @Override
    public void permite(Entidad entidad) {
        if(entidad.montoTotal() > montoMaximo){
            throw new ReglaDeNegocioException("La regla entidad supero el monto maximo");
        }
    }
}
