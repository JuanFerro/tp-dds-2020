package OperacionDeEgresos;

import Fixtures.Fixture;
import Organizaciones.Organizacion;
import Usuario.*;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;

public class EgresoTest {

    private Fixture fixture;

    private Proveedor pablito;
    private MedioDePago tarjeta;
    private Item pepito;
    private List<Item> itemsDePepitos;
    private DocumentoComercial doc;

    private Egreso compraPepitos;
    private Usuario nico;

    @Before
    public void cargarDatos() {

        fixture = new Fixture();
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);

        tarjeta = new MedioDePago(2, TipoMedioDePago.TARJETA_CREDITO);
        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

        doc = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1);

        compraPepitos = new Egreso(pablito, 150, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 150, itemsDePepitos, pablito);

        nico = new Usuario("leNick2", "miContraRealEs12", "miContraRealEs12", TipoUsuario.ESTANDAR, new Organizacion());
        compraPepitos.darDeAltaRevisorDeCompra(nico);

    }

    @Test
    public void recibirMensajeDeLaValidacion() {
        Presupuesto presupuesto2 = new Presupuesto(250, doc, itemsDePepitos, pablito);
        compraPepitos.agregarPresupuestos(presupuesto2);

        RepositorioEgresos.instance().validarLosPendientes();
        assertTrue(nico.bandejaDeUsuarioContieneMensaje("validacion sin errores"));
    }

    @Test
    public void recibirMensajeDeLaValidacionConError() {
        RepositorioEgresos.instance().validarLosPendientes();
        assertTrue(nico.bandejaDeUsuarioContieneMensaje("No tiene la cantidad de presupuestos requerida"));
    }
}