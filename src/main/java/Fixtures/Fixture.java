package Fixtures;


import OperacionDeEgresos.*;
import Organizaciones.Categoria;
import Organizaciones.EntidadBase;
import Organizaciones.EntidadJuridica;

import java.util.ArrayList;
import java.util.List;

public class Fixture {

    MedioDePago medio1 = new MedioDePago(1, TipoMedioDePago.TARJETA_CREDITO);
    MedioDePago medio2 = new MedioDePago(2, TipoMedioDePago.TARJETA_DEBITO);
    MedioDePago medio3 = new MedioDePago(3, TipoMedioDePago.TARJETA_CREDITO);
    DocumentoComercial doc = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1);

    public DireccionPostal getDireccionPostal(){
        Ciudad capitalFederal = new Ciudad("capitalFederal","213");
        List<Ciudad> ciudades = new ArrayList<Ciudad>();
        ciudades.add(capitalFederal);
        Provincia capFederal = new Provincia ("capital federal",ciudades,"507");
        List<Provincia> provincias = new ArrayList<Provincia>();
        provincias.add(capFederal);
        Pais argentina = new Pais ("argentina", "peso", provincias, "804");
        DireccionPostal laCasaDeLuis = new DireccionPostal("Don Bosco", 124, 3, 3, capitalFederal, capFederal, argentina, 1178);

        return laCasaDeLuis;
    }

    public Proveedor getProveedor(String razonSocial, int cuil, int cuit) {
        DireccionPostal direccionPostal = this.getDireccionPostal();
        return new Proveedor(razonSocial, cuil, cuit, direccionPostal);
    }

    public List<Item> cargarListasDeEgresosOPresupuestos(Item... item){
        int i;
        List<Item> listaDeItems = new ArrayList<Item>();

        for(i = 0; i < item.length; i++){
           listaDeItems.add(item[i]);
        }

        return listaDeItems;
    }

    public void cargarEgresosDeProveedores(Proveedor proveedor1, Proveedor proveedor2, List<Item> ListaItemsEgreso1, List<Item> ListaItemsEgreso2, java.time.LocalDate dia1, java.time.LocalDate dia2){

        Egreso egreso1 = new Egreso(proveedor1, 200, medio1, ListaItemsEgreso1, dia1, CriterioSeleccionProveedor.MenorValor);
        Egreso egreso2 = new Egreso(proveedor2, 300, medio2, ListaItemsEgreso2, dia2, CriterioSeleccionProveedor.MenorValor);
        egreso1.setDocumentoComercial(doc);
        egreso2.setDocumentoComercial(doc);

    }

    public void cargarUnPresupuestoYAgregarAEgreso(Egreso egreso, int total, List<Item> items, Proveedor proveedor){

        Presupuesto presupuesto = new Presupuesto(total, doc, items, proveedor);
        egreso.agregarPresupuestos(presupuesto);
    }

    public Egreso crearEgresoEtiquetaYAgregarlaAEgreso(Proveedor proveedor1, int total, List<Item> ListaItemsEgreso1, java.time.LocalDate dia1, EntidadJuridica entidad, Etiqueta etiqueta){

        Egreso egreso1 = new Egreso(proveedor1, total, medio3, ListaItemsEgreso1, dia1, CriterioSeleccionProveedor.MenorValor);
        egreso1.agregarEtiqueta(etiqueta);
        egreso1.setDocumentoComercial(doc);
        entidad.agregarOperacionDeEgreso(egreso1);

        return egreso1;
    }

    public void setearCategoriaYAgregarOperacionesDeEgresoALaEntidad(EntidadJuridica entidad, Categoria categoria, Egreso... egreso){
        int i;

        entidad.setCategoria(categoria);
        for(i = 0; i < egreso.length; i++){
            entidad.agregarOperacionDeEgreso(egreso[i]);
        }
    }

    public void setearCategoriaYAgregarEntidadBase(EntidadJuridica entidad, Categoria categoria, EntidadBase... entidadBase){
        int i;

        entidad.setCategoria(categoria);
        for(i = 0; i < entidadBase.length; i++){
            entidad.agregarEntidadBase(entidadBase[i]);
        }

    }
}
