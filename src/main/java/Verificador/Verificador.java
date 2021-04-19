package Verificador;

import java.util.ArrayList;
import java.util.List;

public class Verificador {
    List<Condicion> condiciones = new ArrayList<>();

    public Verificador(List<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public Verificador() {
        this(new ArrayList<Condicion>());
    }

    public void agregarCondicion(Condicion condicion) {
        this.condiciones.add(condicion);
    }

    public void verificarCondiciones(Object object) {
        this.condiciones.stream().forEach(condicion -> condicion.verificar(object));
    }
}
