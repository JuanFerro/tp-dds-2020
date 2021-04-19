package OperacionDeEgresos;

import Excepciones.NoEgressContainsItemException;
import Fixtures.Fixture;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class PresupuestosTest {
    private Fixture fixture;

    private Proveedor pablito;
    private Proveedor juancito;

    private Item item1;
    private Item item2;
    private Item item3;
    private List<Item> ListaItemsEgreso1;
    private List<Item> ListaItemsEgreso2;
    private List<Item> ListaItemsPresupuesto;

    private DocumentoComercial doc1;

    @Before
    public void cargarDatos(){
        fixture = new Fixture();
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);
        juancito = fixture.getProveedor("bla bla bla", 2024, 2025);

        item1 = new Item("pepitos", "descripcion", 150);
        item2 = new Item("cheetos", "descripcion", 250);
        item3 = new Item("lays", "descripcion", 500);

        ListaItemsEgreso1 = fixture.cargarListasDeEgresosOPresupuestos(item1, item2);
        ListaItemsEgreso2 = fixture.cargarListasDeEgresosOPresupuestos(item2);
        ListaItemsPresupuesto = fixture.cargarListasDeEgresosOPresupuestos(item1, item2, item3);

        doc1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1);
    }

    @Test(expected = NoEgressContainsItemException.class)
    public void UnItemNoEstaAsociadoANingunEgreso()   {
        fixture.cargarEgresosDeProveedores(pablito, juancito, ListaItemsEgreso1, ListaItemsEgreso2, LocalDate.now(), LocalDate.now());
        Presupuesto presupuesto = new Presupuesto(150, doc1, ListaItemsPresupuesto, pablito);
    }

    @Test
    public void SePuedeCrearPresupuestoAlTenerCadaItemUnEgresoAsociado()   {
        ListaItemsEgreso1.add(item3);
        fixture.cargarEgresosDeProveedores(pablito, juancito, ListaItemsEgreso1, ListaItemsEgreso2, LocalDate.now(), LocalDate.now());
        Presupuesto presupuesto = new Presupuesto(150, doc1, ListaItemsPresupuesto, pablito);
    }
}
