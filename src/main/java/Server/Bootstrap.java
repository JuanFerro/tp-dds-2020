package Server;


import Fixtures.Fixture;
import OperacionDeEgresos.*;
import Organizaciones.*;
import Usuario.*;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public static void main(String[] args) {
        new Bootstrap().init();
    }


    public Fixture fixture;
    public Proveedor pablito;
    public Etiqueta etiqueta1;
    public Etiqueta etiqueta2;
    public Etiqueta etiqueta3;
    private MedioDePago tarjeta;
    private Item pepito;
    private List<Item> itemsDePepitos;

    public void init(){
        withTransaction(() ->{

            try {
                Organizacion org1 = new Organizacion();
                Categoria categoriaDefault = new Categoria("Default");
                org1.agregarCategoria(categoriaDefault);
                EntidadBase eb1 = new EntidadBase("Entidad Base 1", "Entidad Base 1", categoriaDefault);
                EntidadJuridica ej1 = new EntidadJuridica("Entidad jurídica 1", "Entidad jurídica 1", (long) 123456789,1351687, TipoEntidadJuridica.OSC, categoriaDefault);
                ej1.agregarEntidadBase(eb1);
                org1.agregarEntidadBase(eb1);
                org1.agregarEntidadJuridica(ej1);
                Usuario usuarioTest = new Usuario("testeado testeadin", "Contra135", "Contra135", TipoUsuario.ESTANDAR, org1);

                fixture = new Fixture();
                pablito = fixture.getProveedor("bli bli bli", 2023, 2023);

                tarjeta = new MedioDePago(2, TipoMedioDePago.TARJETA_CREDITO);

                pepito = new Item("pepitos", "descripcion", 150);

                itemsDePepitos = fixture.cargarListasDeEgresosOPresupuestos(pepito);

                Egreso egreso = new Egreso(pablito, 200, tarjeta, itemsDePepitos, LocalDate.of(1998, 04, 01), CriterioSeleccionProveedor.MenorValor);

                egreso.darDeAltaRevisorDeCompra(usuarioTest);

                MensajeValidacionEgreso mensaje = new MensajeValidacionEgreso("los pochoclos se comen salados", egreso);

                MensajeValidacionEgreso mensaje2 = new MensajeValidacionEgreso("los pochoclos se comen sin azucar", egreso);

                mensaje2.marcarVisto();

                usuarioTest.notificarValidacion(mensaje);
                usuarioTest.notificarValidacion(mensaje2);

                //etiqueta1 = new Etiqueta("Juan");
                //etiqueta2 = new Etiqueta("Sergio");
                //etiqueta3 = new Etiqueta("Papu");

                ej1.agregarOperacionDeEgreso(egreso);
                RepositorioProveedores.instancia.agregar(pablito);
                RepositorioUsuarios.instancia.agregar(usuarioTest);

            } catch (Exception e) {
                e.printStackTrace();
                rollbackTransaction();
            }
        });
    }
}
