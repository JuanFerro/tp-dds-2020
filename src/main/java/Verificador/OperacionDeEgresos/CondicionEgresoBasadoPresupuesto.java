package Verificador.OperacionDeEgresos;

import OperacionDeEgresos.Egreso;
import Verificador.Condicion;

public class CondicionEgresoBasadoPresupuesto extends Condicion<Egreso> {
    protected boolean cumpleCondicion(Egreso egreso) {
        return egreso.presupuestos().stream().anyMatch( presupuesto -> presupuesto.getTotal().equals(egreso.getTotalOperacion()));
    }

    protected String getMensajeExcepcion(){
        return "Ninguno de los presupuestos posee el total del egreso";
    };
}
