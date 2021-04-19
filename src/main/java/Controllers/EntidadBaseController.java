package Controllers;

import Organizaciones.*;
import com.google.common.base.Optional;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class EntidadBaseController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    private List<EntidadBase> filtrarSegunCategoria (List<EntidadBase> entidadesBase, Categoria categoria) {
        return entidadesBase.stream().filter(entidadBase -> entidadBase.getCategoria().getId().equals(categoria.getId())).collect(Collectors.toList()); // filtro segun la categoria
    }

    public ModelAndView listar(Request req, Response res) {
        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        List<Categoria> categorias = organizacion.getCategorias();

        Map<String, Object> model = new HashMap<>();
        List<EntidadBase> entidadesBase = organizacion.getEntidadesBase();

        // Filtro entidadJuridica
        String entidadJuridicaId = StringUtils.isEmpty(req.queryParams("entidad_juridica_id")) ? null : req.queryParams("entidad_juridica_id");

        if (entidadJuridicaId != null) {
            EntidadJuridica entidadJuridicaSeleccionada = RepositorioEntidadJuridica.instancia.buscar(Long.parseLong(entidadJuridicaId));
            entidadesBase =
                    Optional.fromNullable(entidadJuridicaId)
                        .transform(it -> entidadJuridicaSeleccionada.getEntidadesBase()) // filtro segun la entidad juridica
                        .or(entidadesBase);
            model.put("entidadJuridicaSeleccionada", entidadJuridicaSeleccionada);
        }

        // Filtro categoria
        String categoriaId = StringUtils.isEmpty(req.queryParams("categoria_id")) ? null : req.queryParams("categoria_id");

        if (categoriaId != null) {
            Categoria categoria = RepositorioCategorias.instancia.buscar(Long.parseLong(categoriaId));
            if (categoria != null) {
                entidadesBase = filtrarSegunCategoria(entidadesBase, categoria);
                model.put("categoriaSeleccionada", categoria);
            }
        }


        model.put("usuario", req.session().attribute("usuario"));
        model.put("activeEntidadesBase", "active");
        model.put("categorias", categorias);
        model.put("entidadesBase", entidadesBase);
        model.put("entidadesJuridicas", organizacion.getEntidadesJuridicas());

        return new ModelAndView(model, "pages/entidadesBaseLista.hbs");
    }

    private Map<String, Object> modeloParaForm(Request req, EntidadBase entidad) {

        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        List<Categoria> categorias = organizacion.getCategorias();
        List<EntidadJuridica> entidadesJuridicas = organizacion.getEntidadesJuridicas();

        Map<String, Object> model = new HashMap<>();
        model.put("usuario", req.session().attribute("usuario"));

        if (entidad != null) {  // Si estoy editando, pongo primero la opcion que ya posee, así sale seleccionada
            model.put("categoriaSeleccionada", entidad.getCategoria());
            model.put("entidadJuridicaSeleccionada", entidad.getEntidadJuridica());
        }
        model.put("categorias", categorias);
        model.put("entidadesJuridicas", entidadesJuridicas);

        return model;
    }


    public ModelAndView newForm(Request req, Response res) {
        Map<String, Object> model = modeloParaForm(req, null);

        return new ModelAndView(model, "pages/entidadesBaseForm.hbs");
    }

    public ModelAndView editForm(Request req, Response res)  {
        EntidadBase entidad = RepositorioEntidadBase.instancia.buscar(Long.parseLong(req.params("id")));

        Map<String, Object> model = modeloParaForm(req, entidad);
        model.put("entidad", entidad);

        return new ModelAndView(model, "pages/entidadesBaseForm.hbs");
    }

    public EntidadBase getDataFromForm(Request req) {
        String descripcion = req.queryParams("descripcion");
        String nombreFicticio = req.queryParams("nombreFicticio");
        Categoria categoria = RepositorioCategorias.instancia.buscar(Long.parseLong(req.queryParams("categoria_id")));

        EntidadBase entidadBase = new EntidadBase(nombreFicticio, descripcion, categoria);

        return entidadBase;
    }

    public ModelAndView create(Request req, Response res) {
        Organizacion organizacion = RepositorioOrganizaciones.instancia.buscar(req.session().attribute("organizacion_id"));
        EntidadJuridica entidadJuridica = RepositorioEntidadJuridica.instancia.buscar(Long.parseLong(req.queryParams("entidad_juridica_id")));

        EntidadBase nuevaEntidadBase = getDataFromForm(req);

        withTransaction(() -> {
            organizacion.agregarEntidadBase(nuevaEntidadBase);
            entidadJuridica.agregarEntidadBase(nuevaEntidadBase);
            //RepositorioEntidadBase.instancia.agregar(nuevaEntidadBase);
        });
        res.redirect("/entidadesBase");
        return null;
    }

    public ModelAndView edit(Request req, Response res) {
        EntidadJuridica entidadJuridica = RepositorioEntidadJuridica.instancia.buscar(Long.parseLong(req.queryParams("entidad_juridica_id")));

        EntidadBase nuevosDatos = getDataFromForm(req);
        EntidadBase entidad = RepositorioEntidadBase.instancia.buscar(Long.parseLong(req.params("id")));

        withTransaction(() -> {
            entidad.setData(nuevosDatos);
            entidad.setEntidadJuridica(entidadJuridica);
            //RepositorioEntidadBase.instancia.agregar(entidad);
        });
        res.redirect("/entidadesBase");
        return null;
    }
    
    

}
