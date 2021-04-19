package Organizaciones;


import Fixtures.Fixture;
import OperacionDeEgresos.*;
import Organizaciones.Reglas.ReglaBloqueante;
import Organizaciones.Reglas.ReglaBloquearSiSuperoMonto;
import Organizaciones.Reglas.ReglaDeNegocioException;
import Organizaciones.Reglas.TipoDeRegla;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class EntidadTest {
    Categoria categoriaDefault;
    EntidadJuridica entidadJuridica;
    EntidadBase entidadBase;

    private Fixture fixture;
    private Proveedor pablito;
    private Item pepito;
    private List<Item> itemsDePepitos;

    private Egreso compraPepitos;

    @Before
    public void before() {
        categoriaDefault = new Categoria("Default");
        entidadJuridica = new EntidadJuridica("Test", "test", (long) 123456789,1351687, TipoEntidadJuridica.OSC, categoriaDefault);
        entidadBase = new EntidadBase("baseTest", "una entidad base", categoriaDefault);

        fixture = new Fixture();
    }

    @Test(expected = ReglaDeNegocioException.class)
    public void bloquearAgregarEntidadBaseAJuridica()  {
        Categoria categoria = new Categoria("Sin Entidades Base");
        categoria.agregarReglaDeNegocio(new ReglaBloqueante(TipoDeRegla.agregarEntidadBaseAJuridica));
        entidadJuridica.setCategoria(categoria);

        try
        {
            entidadJuridica.agregarEntidadBase(entidadBase);
        }
        catch(ReglaDeNegocioException exception)
        {
            String message = "La regla no te permite hacer esta operacion";
            assertEquals(message, exception.getMessage());
            throw exception;
        }

        fail("Tiro mal la excepcion");
    }

    @Test
    public void agregarEntidadBaseAJuridica()  {
        Categoria categoria = new Categoria("Sin Entidades Base");
        categoria.agregarReglaDeNegocio(new ReglaBloqueante(TipoDeRegla.aceptarNuevoEgreso));
        fixture.setearCategoriaYAgregarEntidadBase(entidadJuridica, categoria, entidadBase);

        assertTrue(entidadJuridica.getEntidadesBase().contains(entidadBase));
    }

    @Test(expected = ReglaDeNegocioException.class)
    public void noDejarQueUnaEntidadBaseSeAgregueAUnaJuridica() {
        Categoria categoria = new Categoria("No te vas a agregar a la juridica");
        categoria.agregarReglaDeNegocio(new ReglaBloqueante(TipoDeRegla.agregarseAEntidadJuridica));
        entidadBase.setCategoria(categoria);

        try
        {
            entidadJuridica.agregarEntidadBase(entidadBase);
        }
        catch(ReglaDeNegocioException exception)
        {
            String message = "La regla no te permite hacer esta operacion";
            assertEquals(message, exception.getMessage());
            throw exception;
        }

        fail("Tiro mal la excepcion");
    }

    @Test(expected = ReglaDeNegocioException.class)
    public void bloquearAgregarUnEgresoSiSuperaElMonto() {

        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);

        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);
        MedioDePago tarjeta = new MedioDePago(3, TipoMedioDePago.TARJETA_CREDITO);

        compraPepitos = new Egreso(pablito, 150, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        Egreso compraPepitos2 = new Egreso(pablito, 15020, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        Categoria categoria = new Categoria("NoAgregarSiSePasaDelMonto");
        categoria.agregarReglaDeNegocio(new ReglaBloquearSiSuperoMonto(100));
        fixture.setearCategoriaYAgregarOperacionesDeEgresoALaEntidad(entidadJuridica, categoria, compraPepitos);

        try
        {
            entidadJuridica.agregarOperacionDeEgreso(compraPepitos2);
        }
        catch(ReglaDeNegocioException exception)
        {
            String message = "La regla entidad supero el monto maximo";
            assertEquals(message, exception.getMessage());
            throw exception;
        }

        fail("Tiro mal la excepcion");
    }

    @Test
    public void agregarUnEgresoSiNoSuperaElMonto() {
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);
        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);
        MedioDePago tarjeta = new MedioDePago(3, TipoMedioDePago.TARJETA_CREDITO);

        compraPepitos = new Egreso(pablito, 150, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        compraPepitos.setDocumentoComercial(new DocumentoComercial(TipoDocumentoComercial.FACTURA, 500));
        Egreso compraPepitos2 = new Egreso(pablito, 150, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        compraPepitos2.setDocumentoComercial(new DocumentoComercial(TipoDocumentoComercial.FACTURA, 500));
        Categoria categoria = new Categoria("NoAgregarSiSePasaDelMonto");
        categoria.agregarReglaDeNegocio(new ReglaBloquearSiSuperoMonto(400));
        fixture.setearCategoriaYAgregarOperacionesDeEgresoALaEntidad(entidadJuridica, categoria, compraPepitos, compraPepitos2);
    }

    @Test
    public void obtenerEgresosPorEtiqueta() {
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);
        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

        Etiqueta etiqueta1 = new Etiqueta("pepitos");
        Etiqueta etiqueta2 = new Etiqueta("pepitos2");

        compraPepitos = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(1998,04,01),entidadJuridica, etiqueta1);
        Egreso compraPepitos2 = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(1998,04,01),entidadJuridica, etiqueta2);

        assertEquals(entidadJuridica.egresosConEtiqueta(entidadJuridica.getOperacionesDeEgresos(), etiqueta1).size(), 1);

    }

    @Test
    public void obtenerEgresosDeUltimoMes() {
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);
        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

        Etiqueta etiqueta1 = new Etiqueta("pepitos");
        Etiqueta etiqueta2 = new Etiqueta("pepitos2");

        compraPepitos = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(2020,11,27),entidadJuridica, etiqueta1);
        Egreso compraPepitos2 = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(2020,10,27),entidadJuridica, etiqueta2);

        assertEquals(entidadJuridica.egresosDelUltimoMes(entidadJuridica.getOperacionesDeEgresos()).size(), 1);
    }

    @Test
    public void crearReporte() {
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);
        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

        Etiqueta etiqueta1 = new Etiqueta("pepitos");
        Etiqueta etiqueta2 = new Etiqueta("pepitos2");

        compraPepitos = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(2020,12,27), entidadJuridica, etiqueta1);
        Egreso compraPepitos2 = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(2020,11,27), entidadJuridica, etiqueta2);
        Egreso compraPepitos3 = fixture.crearEgresoEtiquetaYAgregarlaAEgreso(pablito,150, itemsDePepitos, LocalDate.of(2020,10,27), entidadJuridica, etiqueta1);

        entidadJuridica.crearReporteDeEgresosMensual(etiqueta1);

        assertTrue(entidadJuridica.getReportes().get(0).getEgresos().contains(compraPepitos));
    }
}