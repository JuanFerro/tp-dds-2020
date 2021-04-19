package OperacionDeEgresos;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteEgresos {
    Date fecha;
    List<Egreso> egresos = new ArrayList<>();

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public ReporteEgresos(List<Egreso> egresos){
        this.fecha = java.sql.Date.valueOf(LocalDate.now());
        this.egresos = egresos;
    }
}
