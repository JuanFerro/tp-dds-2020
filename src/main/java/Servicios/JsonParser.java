package Servicios;

import Excepciones.InvalidPostalDirectionException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonParser {
    Map<String, Object> parseJsonObject(String json) {
        Gson gson = new Gson();
        Type tipoListaProperties = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(json, tipoListaProperties);
        return map;
    }

    List<Map> parseJsonList(String json) {
        Gson gson = new Gson();
        Type tipoListaProperties = new TypeToken<List<Map>>() {
        }.getType();
        List<Map> properties = gson.fromJson(json, tipoListaProperties);
        return properties;
    }

}