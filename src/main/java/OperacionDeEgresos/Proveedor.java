package OperacionDeEgresos;

import Organizaciones.EntidadBase;
import Organizaciones.EntidadJuridica;
import Otros.PersistedEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Proveedor extends PersistedEntity {
    private String razonSocial;
    private int cuil;
    private int cuit;
    @Column(nullable = true)
    @Embedded
    private DireccionPostal direccionPostal;

    public Proveedor () { }

    public Proveedor(String razonSocial, int cuil, int cuit, DireccionPostal direccionPostal) {
        this.razonSocial = razonSocial;
        this.cuil = cuil;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
    }


    public DireccionPostal direccionPostal() { return direccionPostal; }

    public String monedaSegunElPais() {
        return direccionPostal().monedaSegunElPais();
    }

    public String getRazonSocial(){
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial){
         this.razonSocial = razonSocial;
    }

}