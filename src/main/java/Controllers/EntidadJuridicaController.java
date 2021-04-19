package Controllers;

import Organizaciones.*;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

import javax.transaction.Transaction;
import java.util.*;
import java.util.stream.Collectors;

public class EntidadJuridicaController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps  {

    private List<EntidadJuridica> filtrarSegunCategoria (List<EntidadJuridica> entidadJuridicas, Categoria categoria) {
        return entidadJuridicas.stream().filter(entidadBase -> entidadBase.getCategoria().getId().equals(categoria.getId())).collect(Collectors.toList()); // filtro segun la categoria
    }

    public ModelAndView listar(Request req, Response res) {
        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        List<Categoria> categorias = organizacion.getCategorias();

        List<EntidadJuridica> entidadesJuridicas = organizacion.getEntidadesJuridicas();

        // Filtro categoria
        String categoriaId = StringUtils.isEmpty(req.queryParams("categoria_id")) ? null : req.queryParams("categoria_id");
        Map<String, Object> model = new HashMap<>();

        if (categoriaId != null) {
            Categoria categoria = RepositorioCategorias.instancia.buscar(Long.parseLong(categoriaId));
            if (categoria != null) {
                entidadesJuridicas = filtrarSegunCategoria(entidadesJuridicas, categoria);
                model.put("categoriaSeleccionada", categoria);
            }
        }

        model.put("usuario", req.session().attribute("usuario"));
        model.put("activeEntidadesJuridicas", "active");
        model.put("categorias", categorias);
        model.put("entidadesJuridicas", entidadesJuridicas);

        return new ModelAndView(model, "pages/entidadesJuridicasLista.hbs");
    }

    private Map<String, Object> modeloParaForm(Request req, EntidadJuridica entidad) {
        List<TipoEntidadJuridica> tiposEntidad = new ArrayList<>();
        Collections.addAll(tiposEntidad, TipoEntidadJuridica.values());

        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        List<Categoria> categorias = organizacion.getCategorias();

        Map<String, Object> model = new HashMap<>();
        model.put("usuario", req.session().attribute("usuario"));

        if (entidad != null) {  // Si estoy editando, pongo primero la opcion que ya posee, así sale seleccionada
            model.put("categoriaSeleccionada", entidad.getCategoria());
            model.put("tipoEntidadSeleccionada", entidad.getTipoEntidadJuridica());
        }
        model.put("categorias", categorias);
        model.put("tiposEntidad", tiposEntidad);

        return model;
    }


    public ModelAndView newForm(Request req, Response res) {
        Map<String, Object> model = modeloParaForm(req, null);

        return new ModelAndView(model, "pages/entidadesJuridicasForm.hbs");
    }

    public ModelAndView editForm(Request req, Response res)  {
        EntidadJuridica entidad = RepositorioEntidadJuridica.instancia.buscar(Long.parseLong(req.params("id")));

        Map<String, Object> model = modeloParaForm(req, entidad);
        model.put("entidad", entidad);

        return new ModelAndView(model, "pages/entidadesJuridicasForm.hbs");
    }

    public EntidadJuridica getDataFromForm(Request req) {
        String razonSocial = req.queryParams("razonSocial");
        String nombreFicticio = req.queryParams("nombreFicticio");
        Long cuit = Long.parseLong(req.queryParams("cuit"));
        int direccionPostal = Integer.parseInt(req.queryParams("direccionPostal"));
        TipoEntidadJuridica tipoEntidadJuridica = TipoEntidadJuridica.values()[Integer.parseInt(req.queryParams("tipoEntidadJuridica"))];
        Categoria categoria = RepositorioCategorias.instancia.buscar(Long.parseLong(req.queryParams("categoria_id")));
        EntidadJuridica entidadJuridica = new EntidadJuridica(razonSocial, nombreFicticio, cuit, direccionPostal, tipoEntidadJuridica, categoria);

        return entidadJuridica;
    }

    public ModelAndView create(Request req, Response res) {
        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        EntidadJuridica nuevaEntidadJuridica = getDataFromForm(req);

        withTransaction(() -> {
            organizacion.agregarEntidadJuridica(nuevaEntidadJuridica);
            //RepositorioEntidadJuridica.instancia.agregar(nuevaEntidadJuridica);
        });
        res.redirect("/entidadesJuridicas");
        return null;
    }

    public ModelAndView edit(Request req, Response res) {
        EntidadJuridica nuevosDatos = getDataFromForm(req);
        EntidadJuridica entidad = RepositorioEntidadJuridica.instancia.buscar(Long.parseLong(req.params("id")));


        withTransaction(() -> {
            entidad.setData(nuevosDatos);
            //RepositorioEntidadJuridica.instancia.agregar(entidad);
        });
        res.redirect("/entidadesJuridicas");
        return null;
    }
}
