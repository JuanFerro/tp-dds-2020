package Organizaciones;

import Organizaciones.Reglas.TipoDeRegla;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EntidadJuridica extends Entidad{

    private String razonSocial;
    private Long cuit;
    private int direccionPostal;
    private int codigoDeInscripcionIGJ;

    @Enumerated(EnumType.ORDINAL)
    private TipoEntidadJuridica tipoEntidadJuridica;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "entidad_juridica_id")
    private List<EntidadBase> entidadesBase = new ArrayList<>();

    public EntidadJuridica () {}

    public EntidadJuridica(String razonSocial, String nombreFicticio, Long cuit, int direccionPostal, TipoEntidadJuridica tipoEntidadJuridica, Categoria categoria){
        super(categoria, nombreFicticio);
        this.codigoDeInscripcionIGJ = 0;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
        this.tipoEntidadJuridica = tipoEntidadJuridica;
    }

    public void setData (EntidadJuridica entidad) {
        setCategoria(entidad.categoria);
        this.codigoDeInscripcionIGJ = 0;
        this.razonSocial = entidad.razonSocial;
        this.nombreFicticio = entidad.nombreFicticio;
        this.cuit = entidad.cuit;
        this.direccionPostal = entidad.direccionPostal;
        this.tipoEntidadJuridica = entidad.tipoEntidadJuridica;
    }

    public List<EntidadBase> getEntidadesBase() {
        return entidadesBase;
    }
    public Long getCuit() { return cuit; }
    public String getRazonSocial() { return razonSocial; }
    public TipoEntidadJuridica getTipoEntidadJuridica() { return tipoEntidadJuridica; }
    public int getDireccionPostal() { return direccionPostal; }

    public void agregarEntidadBase(EntidadBase entidadBase) {
        categoria.verificar(this, TipoDeRegla.agregarEntidadBaseAJuridica);
        entidadBase.puedeAnadirse();
        this.entidadesBase.add(entidadBase);
    }

    public void sacarEntidadBase(EntidadBase entidadBase) {
        this.entidadesBase.remove(entidadBase);
    }

    public void setCodigoDeInscripcionIGJ(int codigoDeInscripcionIGJ){
        this.codigoDeInscripcionIGJ = codigoDeInscripcionIGJ;
    }

}