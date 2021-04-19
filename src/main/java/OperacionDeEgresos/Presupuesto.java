package OperacionDeEgresos;

import Excepciones.NoEgressContainsItemException;
import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Presupuesto extends PersistedEntity {
    private Integer total;
    @Embedded
    private DocumentoComercial documentoComercial;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "presupuesto_id")
    private List<Item> listaItems = new ArrayList<>();

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    public Presupuesto () {}

    public Presupuesto(Integer total, DocumentoComercial documentoComercial, List<Item> listaItems, Proveedor proveedor) {
        this.verificarItems(listaItems);
        this.total = total;
        this.documentoComercial = documentoComercial;
        this.listaItems = listaItems;
        this.proveedor = proveedor;
    }


    public Integer getTotal(){return total;}
    public String tipoMoneda() { return proveedor.monedaSegunElPais(); }

    private boolean cadaItemEstaAsociadoAUnEgreso(List<Item> listaDeItems) {
        return listaDeItems.stream().allMatch(unItem -> RepositorioEgresos.instance().algunEgresoTieneElItem(unItem));
    }

    private void verificarItems(List<Item> listaItems){
        if(!this.cadaItemEstaAsociadoAUnEgreso(listaItems))
        throw new NoEgressContainsItemException("el item no esta asociado a ningún egreso");
    }



}
