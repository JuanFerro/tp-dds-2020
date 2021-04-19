package Servicios;

import Excepciones.BadStatusApiException;
import Excepciones.InvalidPostalDirectionException;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class ServicioUbicacion {

    ServicioRest servicioRest = new ServicioRest(); //en caso de que haya muchos rest se podria pasar por constructor.
    JsonParser jsonParser = new JsonParser();
    UtilesMap utilesMap= new UtilesMap();

    // PAISES
    public List<Map> paises() {
        String json = servicioRest.getJsonFromApiGet("classified_locations/countries");
        return jsonParser.parseJsonList(json);
    }

    public void validarPais (String pais) {
        utilesMap.buscarIdEn(paises(), pais); // El buscar ID es el que tira la expcecion
    }

    // PROVINCIAS
    public List<Map> provinciasDe(String pais) {
        String idPais = utilesMap.buscarIdEn(paises(), pais).toString();
        String json = servicioRest.getJsonFromApiGet("classified_locations/countries/" + idPais);
        Map<String, Object> prop = jsonParser.parseJsonObject(json);
        return (List<Map>) prop.get("states");
    }

    public void validarProvincia (String provincia, String pais) {
        utilesMap.buscarIdEn(provinciasDe(pais), provincia);
    }

    // CIUDADES
    public List<Map> ciudadesDe(String provincia, String pais) {
        String idProvincia = utilesMap.buscarIdEn(provinciasDe(pais), provincia).toString();
        String json = servicioRest.getJsonFromApiGet("classified_locations/states/" + idProvincia);
        Map<String, Object> prop = jsonParser.parseJsonObject(json);
        return (List<Map>) prop.get("cities");
    }

    public void validarCiudad (String ciudad, String provincia, String pais) {
        utilesMap.buscarIdEn(ciudadesDe(provincia, pais), ciudad);
    }

}
