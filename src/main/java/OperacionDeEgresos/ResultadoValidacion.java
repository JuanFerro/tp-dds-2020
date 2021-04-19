package OperacionDeEgresos;

import Excepciones.InvalidConditionException;
import Usuario.MensajeValidacionEgreso;

public class ResultadoValidacion {
    public void invalido(Egreso egreso, InvalidConditionException ex){
        setearEstadoYNotificar(egreso, EstadoEgreso.INVALIDO, ex.getMessage());
    }

    public void valido(Egreso egreso){
        setearEstadoYNotificar(egreso, EstadoEgreso.VALIDO, "validacion sin errores");
    }

    private void setearEstadoYNotificar(Egreso egreso, EstadoEgreso estado, String message){
        MensajeValidacionEgreso mensaje = new MensajeValidacionEgreso(message, egreso);
        egreso.setEstado(estado);
        egreso.notificarALosRevisores(mensaje);
    }
}
