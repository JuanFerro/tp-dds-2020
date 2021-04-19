package Controllers;
import Organizaciones.EntidadJuridica;
import Organizaciones.RepositorioEntidadJuridica;
import Usuario.*;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MensajesController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public ModelAndView listar(Request req, Response res) {
        Usuario usuario = RepositorioUsuarios.instancia.buscar(req.session().attribute("usuario_id"));

        String noVistos = req.queryParams("noVistos");

        List<MensajeValidacionEgreso> mensajes;

        Map<String, Object> model = new HashMap<>();

        if(noVistos == null) {
            mensajes = usuario.getBandejaDeMensajes();
            model.put("activeTodos", "true");
        }
        else {
            mensajes = usuario.getMensajesNoVistos();
            model.put("activeNoVistos", "true");
        }
        if (usuario.getMensajesNoVistos().size() == 0) {
            model.put("sinMensajesNuevos", "true");
        }


        model.put("usuario", usuario.getUsername());
        model.put("activeMensajes", "active");
        model.put("mensajes", mensajes);
        model.put("cantidadNoVistos", usuario.getMensajesNoVistos().size());

        return new ModelAndView(model, "pages/mensajes.hbs");
    }

    public ModelAndView marcarVisto(Request req, Response res) {
        MensajeValidacionEgreso mensaje = RepositorioMensajes.instancia.buscar(Long.parseLong(req.params("id")));

        mensaje.marcarVisto();

        withTransaction(() -> {
            RepositorioMensajes.instancia.agregar(mensaje);
        });

        res.redirect("/mensajes");

        return null;
    }
}