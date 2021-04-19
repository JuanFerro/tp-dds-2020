package Verificador.OperacionDeEgresos;

import OperacionDeEgresos.Egreso;
import OperacionDeEgresos.Presupuesto;
import Verificador.Condicion;

public class CondicionCriterioSeleccion extends Condicion<Egreso> {

    protected boolean cumpleCondicion(Egreso egreso) {
        return this.conseguirPresupuesto(egreso).getTotal().equals(egreso.getTotalOperacion());
    }
    protected Presupuesto conseguirPresupuesto(Egreso egreso){
        return egreso.getCriterioSeleccion().presupuestoSegunCriterio(egreso.presupuestos());
    }
    protected String getMensajeExcepcion(){
        return "El valor del egreso no corresponde con el seleccionado segun el criterio";
    }
}
