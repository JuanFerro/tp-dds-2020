package Servicios;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

public class ServicioMoneda {
    ServicioRest servicioRest = new ServicioRest(); //en caso de que haya muchos rest se podria pasar por constructor.
    JsonParser jsonParser = new JsonParser();
    ServicioUbicacion servicioUbicacion = new ServicioUbicacion();
    UtilesMap utilesMap=new UtilesMap();

    public String monedaDelPais(String pais) {
        String idCurrency = utilesMap.getKeyDeMapEn(servicioUbicacion.paises(), "currency_id", pais).toString();
        String json = servicioRest.getJsonFromApiGet("currencies/" + idCurrency);
        Map<String, Object> prop = jsonParser.parseJsonObject(json);
        return prop.get("symbol").toString();
    }
}
