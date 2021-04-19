package Controllers;

import OperacionDeEgresos.*;
import Organizaciones.*;
import OperacionDeEgresos.RepositorioItem;
import Usuario.Usuario;
import org.hibernate.Session;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EgresosController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {


    public ModelAndView listar(Request req, Response res) {
        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));

        List<Egreso> egresos = organizacion.egresosDeLaOrganizacion();

        Map<String, Object> model = new HashMap<>();

        model.put("usuario", req.session().attribute("usuario"));
        model.put("activeEgresos", "active");
        model.put("egresos", egresos);

        return new ModelAndView(model, "pages/egresos.hbs");
    }

    public ModelAndView newForm(Request req, Response res) {
        List<TipoMedioDePago> mediosDePago = new ArrayList<>();
        Collections.addAll(mediosDePago, TipoMedioDePago.values());

        List<CriterioSeleccionProveedor> criterios = new ArrayList<>();
        Collections.addAll(criterios, CriterioSeleccionProveedor.values());

        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));

        List<Proveedor> proveedores = RepositorioProveedores.instancia.todos();
        Map<String, Object> model = new HashMap<>();

        model.put("usuario", req.session().attribute("usuario"));
        model.put("proveedores", proveedores);
        model.put("tiposMedioDePago", mediosDePago);
        model.put("CriteriosDeSeleccionProveedor", criterios);
        model.put("entidadesJuridicas", organizacion.getEntidadesJuridicas());
        model.put("entidadesBase", organizacion.getEntidadesBase());



        return new ModelAndView(model, "pages/egresosForm.hbs");
    }

    public ModelAndView newItemForm(Request req, Response res) {

        Egreso egreso = req.session().attribute("egreso");


        Map<String, Object> model = new HashMap<>();

        model.put("usuario", req.session().attribute("usuario"));
        model.put("egreso", egreso);
        model.put("items", egreso.getItems());

        if (egreso.getItems().size() == 0) {
            model.put("sinItems", "true");
        }

        return new ModelAndView(model, "pages/egresosItemForm.hbs");
    }

    public Egreso getDataFromForm(Request req) {
        Proveedor proveedor = RepositorioProveedores.instancia.buscar(Long.parseLong(req.queryParams("proveedor_id")));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/aaaa");
        java.sql.Date fechasql = java.sql.Date.valueOf(req.queryParams("fecha"));
        java.time.LocalDate fecha = fechasql.toLocalDate();
        TipoMedioDePago medioDePago = TipoMedioDePago.values()[Integer.parseInt(req.queryParams("tipoMedioDePago"))];
        int identMedioDePago = Integer.parseInt(req.queryParams("identMedioDePago"));
        CriterioSeleccionProveedor criterio = CriterioSeleccionProveedor.values()[Integer.parseInt(req.queryParams("criterioDeSeleccionProveedor"))];

        Egreso egreso = new Egreso(proveedor, 0,  new MedioDePago(identMedioDePago, medioDePago), new ArrayList<Item>(), fecha, criterio );

        return egreso;
    }


    public ModelAndView createEgreso(Request req, Response res) {

        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        Egreso egreso = getDataFromForm(req);

        req.session().attribute("egreso", egreso);
        req.session().attribute("entidad_id", req.queryParams("entidad_id"));

        req.session().attribute("enCreacion", true);
        res.redirect("/egresos/new/step2");

        return null;
    }

    public ModelAndView detallesDelEgreso(Request req, Response res) {

        Map<String, Object> model = new HashMap<>();
        Egreso egreso = RepositorioEgresos.instance().buscar(Long.parseLong(req.params("id")));
        model.put("usuario", req.session().attribute("usuario"));
        model.put("egreso", egreso);
        model.put("items", egreso.getItems());

        return new ModelAndView(model, "pages/egresosDetalle.hbs");
    }

    public ModelAndView agregarItem (Request req, Response res) {
        Egreso egreso = req.session().attribute("egreso");

        String nombre = req.queryParams("nombre");
        String descripcion = req.queryParams("descripcion");
        Double valor = Double.valueOf(req.queryParams("valor"));

        if(req.session().attribute("enCreacion")) {
            Item item = new Item(nombre, descripcion, valor);
            egreso.agregarItem(item);
            req.session().attribute("egreso", egreso);
            res.redirect("/egresos/new/step2");
            return null;
        };

        withTransaction(() -> {

            Item item = new Item(nombre, descripcion, valor);
            egreso.agregarItem(item);
            RepositorioEgresos.instance().addEgreso(egreso);
        });
        res.redirect("/egresos/" + egreso.getId());
        return null;
    }

    public ModelAndView eliminarItem (Request req, Response res) {
        Egreso egreso = req.session().attribute("egreso");
        Item item = egreso.getItems().get(Integer.parseInt(req.params("iditem")));
        egreso.eliminarItem(item);
        res.redirect("/egresos/new/step2");
        return null;
    }

    public ModelAndView guardarItems (Request req, Response res) {

        Egreso egreso = req.session().attribute("egreso");
        Entidad entidad = RepositorioEntidad.instancia.buscar(Long.parseLong(req.session().attribute("entidad_id")));

        withTransaction(() -> {
            entidad.agregarOperacionDeEgreso(egreso);
            RepositorioEgresos.instance().addEgreso(egreso);
        });

        req.session().removeAttribute("enCreacion");
        res.redirect("/egresos");

        return null;
    }
}
