package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    public static ModelAndView home(Request req, Response res){
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", req.session().attribute("usuario"));

        res.redirect("/egresos");    // TODO: Ver a cual cambiar que sería la mas usada
        return null;
    }
}
