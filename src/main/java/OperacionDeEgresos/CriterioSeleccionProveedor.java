package OperacionDeEgresos;

import Otros.PersistedEntity;

import javax.persistence.Entity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public enum CriterioSeleccionProveedor {
    MenorValor {
        @Override
        public String getNombre() {
            return "Menor Valor";
        }

        @Override
        public Presupuesto presupuestoSegunCriterio(List<Presupuesto> presupuestos) {
            return  presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal)).orElseThrow(NoSuchElementException::new);
        }

    };

    public abstract Presupuesto presupuestoSegunCriterio(List<Presupuesto> presupuestos);

    public abstract String getNombre();

    public int getVal() {
        return this.ordinal();
    }
}
