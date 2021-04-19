package Organizaciones;

import Organizaciones.Reglas.TipoDeRegla;

import javax.persistence.Entity;

@Entity
public class EntidadBase extends Entidad {

    private String descripcion;

    public EntidadBase () {}

    public EntidadBase(String nombreFicticio, String descripcion, Categoria categoria){
        super(categoria, nombreFicticio);
        this.descripcion = descripcion;
    }

    public void setData(EntidadBase entidad) {
        setCategoria(entidad.categoria);
        this.nombreFicticio = entidad.nombreFicticio;
        this.descripcion = entidad.descripcion;
    }

    public void puedeAnadirse(){
        categoria.verificar(this, TipoDeRegla.agregarseAEntidadJuridica);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EntidadJuridica getEntidadJuridica() { return RepositorioEntidadJuridica.instancia.entidadJuridicaQueTiene(this); }

    public void setEntidadJuridica(EntidadJuridica nuevaEntidadJuridica) {
        this.getEntidadJuridica().sacarEntidadBase(this);
        nuevaEntidadJuridica.agregarEntidadBase(this);
    }
}

