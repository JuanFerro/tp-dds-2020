import Excepciones.InvalidConditionException;
import Fixtures.Fixture;
import OperacionDeEgresos.*;
import Organizaciones.Organizacion;
import Usuario.Usuario;
import Usuario.TipoUsuario;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class VerificadorPresupuestosTest {

    private Fixture fixture;

    private Proveedor pablito;
    private MedioDePago tarjeta;
    private Item pepito;
    private List<Item> itemsDePepitos;
    private DocumentoComercial doc;

    private Usuario nico;

    @Before
    public void cargarDatos(){
        fixture = new Fixture();
        pablito = fixture.getProveedor("bli bli bli", 2023, 2023);

        pepito = new Item("pepitos", "descripcion", 150);
        itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

        tarjeta = new MedioDePago(2, TipoMedioDePago.TARJETA_CREDITO);
        doc = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1);

        nico = new Usuario("leNick2", "miContraRealEs12", "miContraRealEs12", TipoUsuario.ESTANDAR, new Organizacion());
    }

    @Test
    public void crearEgresoConMenosPresupuestosQueLaCantidadRequerida() {

        Egreso compraPepitos = new Egreso(pablito, 160, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 170, itemsDePepitos, pablito);

        compraPepitos.darDeAltaRevisorDeCompra(nico);
        RepositorioEgresos.instance().validarLosPendientes();

        assertTrue(nico.bandejaDeUsuarioContieneMensaje("No tiene la cantidad de presupuestos requerida"));
    }

    @Test
    public void crearEgresoSinCumplirCriterioSeleccion() {

        Egreso compraPepitos = new Egreso(pablito, 160, tarjeta, itemsDePepitos, LocalDate.of(1998, 04, 01), CriterioSeleccionProveedor.MenorValor);

        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 150, itemsDePepitos, pablito);
        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 160, itemsDePepitos, pablito);

        compraPepitos.darDeAltaRevisorDeCompra(nico);
        RepositorioEgresos.instance().validarLosPendientes();

        assertTrue(nico.bandejaDeUsuarioContieneMensaje("El valor del egreso no corresponde con el seleccionado segun el criterio"));

    }

    @Test
    public void crearEgresoSinBasarseEnPresupuestos() {

        Egreso compraPepitos = new Egreso(pablito, 150, tarjeta, itemsDePepitos, LocalDate.of(1998, 04,01 ), CriterioSeleccionProveedor.MenorValor);
        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 170, itemsDePepitos, pablito);
        fixture.cargarUnPresupuestoYAgregarAEgreso(compraPepitos, 160, itemsDePepitos, pablito);

        compraPepitos.darDeAltaRevisorDeCompra(nico);
        RepositorioEgresos.instance().validarLosPendientes();

        assertTrue(nico.bandejaDeUsuarioContieneMensaje("Ninguno de los presupuestos posee el total del egreso"));
    }
}


