package Verificador;

import Excepciones.InvalidConditionException;

public abstract class Condicion<T> {

    public final void verificar(T object) {
        if(!this.cumpleCondicion(object)) {
            throw new InvalidConditionException(this.getMensajeExcepcion());
        }
    }

    protected abstract boolean cumpleCondicion(T object);
    protected abstract String getMensajeExcepcion();
}
