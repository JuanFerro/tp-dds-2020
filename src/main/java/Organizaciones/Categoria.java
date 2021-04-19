package Organizaciones;

import Organizaciones.Reglas.ReglaDeNegocio;
import Organizaciones.Reglas.TipoDeRegla;
import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Categoria extends PersistedEntity {

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "categoria_id")
    private List<ReglaDeNegocio> reglasDeNegocio = new ArrayList<ReglaDeNegocio>();

    private String nombre;

    public Categoria() {}
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void agregarReglaDeNegocio(ReglaDeNegocio reglaDeNegocio){
        reglasDeNegocio.add(reglaDeNegocio);
    }

    public void sacarReglaDeNegocio(ReglaDeNegocio reglaDeNegocio){
        reglasDeNegocio.remove(reglaDeNegocio);
    }

    public List<ReglaDeNegocio> reglaDeNegocios () { return reglasDeNegocio; }

    public List<ReglaDeNegocio> reglasPorTipo(TipoDeRegla tipoDeRegla){
        return reglasDeNegocio.stream().filter(regla -> regla.getTipoRegla() == tipoDeRegla).collect(Collectors.toList());
    }

    public void verificar(Entidad entidad, TipoDeRegla tipoDeRegla){
        this.reglasPorTipo(tipoDeRegla).stream().forEach(regla -> regla.permite(entidad));
    }

}

