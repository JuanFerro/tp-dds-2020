package OperacionDeEgresos;

import Servicios.ServicioUbicacion;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class DireccionPostal {
    @Column(nullable = true)
    private String calle;
    @Column(nullable = true)
    private int altura;
    @Column(nullable = true)
    private int dpto;
    @Column(nullable = true)
    private int piso;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "pais_id")
    private Pais pais;
    @Column(nullable = true)
    private int codigoPostal;

    public DireccionPostal () { }

    public DireccionPostal (String calle, int altura, int dpto, int piso, Ciudad ciudad, Provincia provincia, Pais pais, int codigoPostal) {

        this.calle = calle;
        this.altura = altura;
        this.dpto = dpto;
        this.piso = piso;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
    }

    public Ciudad ciudad () { return ciudad; }

    public Provincia provincia () {
        return provincia;
    }

    public Pais pais () {
        return pais;
    }

    public String monedaSegunElPais() {
        return pais.monedaDelPais();
    }
}
