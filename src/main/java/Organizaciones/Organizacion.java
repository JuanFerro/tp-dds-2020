package Organizaciones;

import OperacionDeEgresos.Egreso;
import OperacionDeEgresos.Etiqueta;
import OperacionDeEgresos.RepositorioEgresos;
import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Organizacion  extends PersistedEntity {

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "organizacion_id")
    private List<EntidadBase> entidadesBase = new ArrayList<>();

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "organizacion_id")
    private List<EntidadJuridica> entidadesJuridicas = new ArrayList<>();

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "organizacion_id")
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "organizacion_id")
    private List<Etiqueta> etiquetas = new ArrayList<>();

    public void agregarEntidadBase(EntidadBase entidadBase) {
        entidadesBase.add(entidadBase);
    }

    public void agregarEntidadJuridica(EntidadJuridica entidadJuridica) {
        entidadesJuridicas.add(entidadJuridica);
    }

    public List<EntidadBase> getEntidadesBase() {
        return this.entidadesBase;
    }

    public List<EntidadJuridica> getEntidadesJuridicas() {
        return this.entidadesJuridicas;
    }

    public List<Categoria> getCategorias() {
        return this.categorias;
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public List<Egreso> egresosDeLaOrganizacion(){

        List<EntidadJuridica> entidadesJuridicas = this.getEntidadesJuridicas();
        List<EntidadBase> entidadesBase = this.getEntidadesBase();

        List<Egreso> egresos = entidadesJuridicas.stream().flatMap(en -> en.getOperacionesDeEgresos().stream()).collect(Collectors.toList());
        egresos.addAll(entidadesBase.stream().flatMap(en -> en.getOperacionesDeEgresos().stream()).collect(Collectors.toList()));

        return egresos;
    }

    // ETIQUETAS
    public List<Etiqueta> etiquetas() {
        return etiquetas;
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        etiquetas.add(etiqueta);
    }

    public void eliminarEtiqueta(Etiqueta etiqueta) {
        etiquetas.remove(etiqueta);
    }

}
