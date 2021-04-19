package Organizaciones;

import OperacionDeEgresos.Egreso;
import OperacionDeEgresos.Etiqueta;
import OperacionDeEgresos.ReporteEgresos;
import Organizaciones.Reglas.TipoDeRegla;
import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Entidad extends PersistedEntity {

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "entidad_id")
    protected List<Egreso> operacionesDeEgresos = new ArrayList<>();

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "categoria_id")
    protected Categoria categoria;

    protected String nombreFicticio;

    @Transient
    protected List<ReporteEgresos> reportes = new ArrayList<>();

    public List<ReporteEgresos> getReportes() {
        return reportes;
    }

    protected Entidad () {}

    protected Entidad (Categoria categoria, String nombreFicticio) {
        this.categoria = categoria;
        this.nombreFicticio = nombreFicticio;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Egreso> getOperacionesDeEgresos() {
        return operacionesDeEgresos;
    }

    public void agregarOperacionDeEgreso(Egreso opEgreso){
        categoria.verificar(this, TipoDeRegla.aceptarNuevoEgreso);
        operacionesDeEgresos.add(opEgreso);
    }

    public List<Egreso> egresosConEtiqueta(List<Egreso> operacionesDeEgresos, Etiqueta etiqueta) {
        return operacionesDeEgresos.stream().filter(eg -> eg.tieneEtiqueta(etiqueta)).collect(Collectors.toList());
    }

    public int montoTotal() {
        return operacionesDeEgresos.stream().mapToInt(egreso -> egreso.getTotalOperacion()).sum();
    }

    public List<Egreso> egresosDelUltimoMes(List<Egreso> operacionesDeEgresos){
        java.sql.Date fechaActualMenos1Mes = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));
        return operacionesDeEgresos.stream().filter(eg -> eg.getFecha().after(fechaActualMenos1Mes)).collect(Collectors.toList());
    }

    public void crearReporteDeEgresosMensual(Etiqueta etiqueta) {
        reportes.add(new ReporteEgresos(egresosConEtiqueta(this.egresosDelUltimoMes(operacionesDeEgresos), etiqueta)));
    }

}
