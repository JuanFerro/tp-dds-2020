package Verificador.OperacionDeEgresos;

import OperacionDeEgresos.Egreso;
import Verificador.Condicion;

import java.util.function.Function;

public class CondicionCantidadRequeridaPresupuestos extends Condicion<Egreso> {

    protected boolean cumpleCondicion(Egreso egreso) {
       return egreso.presupuestos().size() == egreso.getPresupuestosNecesarios();
    }

    protected String getMensajeExcepcion(){
        return "No tiene la cantidad de presupuestos requerida";
    };
}
