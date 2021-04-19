package Server;

import Controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Request;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.StringUtils;

import static spark.Spark.halt;

public class Router {

    public static void configure() {
        HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();


        Spark.staticFiles.location("/public");

        Spark.before((request, response) -> {
            if (!laRutaEs(request, "/login") && !usuarioLogueado(request)) {
                response.redirect("/login");
            } else if (laRutaEs(request,"/login") && usuarioLogueado(request)) {
                response.redirect("/");
            }
        });

        Spark.after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.getEntityManager().clear();
            PerThreadEntityManagers.closeEntityManager();
        });

        Spark.get("/login", LoginController::show, engine);
        Spark.post("/login", LoginController::login, engine);
        Spark.post("/logout", LoginController::logout, engine);
        Spark.get("/", HomeController::home, engine);

        // Mensajes

        MensajesController mensajesController = new MensajesController();

        Spark.get("/mensajes", mensajesController::listar, engine);
        Spark.post("/mensajes/:id", mensajesController::marcarVisto, engine);

        // Entidades Juridicas
        EntidadJuridicaController entidadJuridicaController = new EntidadJuridicaController();

        Spark.get("/entidadesJuridicas", entidadJuridicaController::listar, engine);
        Spark.get("/entidadesJuridicas/new", entidadJuridicaController::newForm, engine);
        Spark.post("/entidadesJuridicas", entidadJuridicaController::create, engine);
        Spark.get("/entidadesJuridicas/:id", entidadJuridicaController::editForm, engine);
        Spark.post("/entidadesJuridicas/:id", entidadJuridicaController::edit, engine);

        // Entidades Base
        EntidadBaseController entidadBaseController = new EntidadBaseController();

        Spark.get("/entidadesBase", entidadBaseController::listar, engine);
        Spark.get("/entidadesBase/new", entidadBaseController::newForm, engine);
        Spark.post("/entidadesBase", entidadBaseController::create, engine);
        Spark.get("/entidadesBase/:id", entidadBaseController::editForm, engine);
        Spark.post("/entidadesBase/:id", entidadBaseController::edit, engine);

        // Egresos
        EgresosController egresosController = new EgresosController();

        Spark.get("/egresos", egresosController::listar, engine);
        Spark.get("/egresos/new/step1", egresosController::newForm, engine); //PUT
        Spark.get("/egresos/new/step2", egresosController::newItemForm, engine);
        Spark.post("/egresos/new/step1", egresosController::createEgreso, engine);
        Spark.get("/egresos/:id", egresosController::detallesDelEgreso, engine);
        Spark.post("/egresos", egresosController::guardarItems, engine);    // ESTO DEBERIA SER UN PUTR UN PUT
        Spark.post("/egresos/new/step2", egresosController::agregarItem, engine);
        Spark.post("/egresos/new/step2/delete/:iditem", egresosController::eliminarItem, engine);
  // Es un post porque html no me deja mandar DELETES
    }

    public static boolean usuarioLogueado(Request request) {
        return !StringUtils.isEmpty(request.session().attribute("usuario"));
    }

    public static boolean laRutaEs(Request request, String route) {
        return request.pathInfo().equals(route);
    }
}