package Controllers;

import Usuario.RepositorioUsuarios;
import Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {


    public static ModelAndView show(Request req, Response res){
        return new ModelAndView(null, "login.hbs");
    }

    public static ModelAndView login(Request req, Response res) {
        String username = req.queryParams("username");
        String password = req.queryParams("password");

        try {
            Usuario usuario = RepositorioUsuarios.instancia.validarCredenciales(username, password);
            req.session().attribute("usuario", username);
            req.session().attribute("usuario_id", usuario.getId());
            // Aca se pueden cargar todos los valores que se quieran para usar en la sesion del usuario logueado
            req.session().attribute("organizacion_id", usuario.organizacion().getId());


            res.redirect("/");
            return null;
        } catch (NoResultException e) {
            Map<String, Object> model = new HashMap<>();
            model.put("errores", "El nombre de usuario y/o la contraseña es incorrecta");
            return new ModelAndView(model, "login.hbs");
        }
    }

    public static ModelAndView logout(Request req, Response res) {
        req.session().attribute("usuario", "");
        res.redirect("/login");
        return null;
    }
}
