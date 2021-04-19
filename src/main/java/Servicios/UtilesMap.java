package Servicios;

import Excepciones.InvalidPostalDirectionException;

import java.util.List;
import java.util.Map;

public class UtilesMap {

    private boolean propertyTieneValue(Map map, String key, Object value) {
        return map.get(key).equals(value);
    }

    public Map buscarRegistroQueTengaElValue(List<Map> list, String value) {
        return list.stream().filter(el -> propertyTieneValue(el, "name", value)).findAny().orElse(null);
    }

    public Object getKeyDeMapEn(List<Map> list, String key, String value) {
        Map map = buscarRegistroQueTengaElValue(list, value);
        if (map != null) {
            return map.get(key);
        }
        throw new InvalidPostalDirectionException(value + " no se encuentra en la lista de las apis");
    }

    public Object buscarIdEn(List<Map> list, String value) {
        return getKeyDeMapEn(list, "id", value);
    }

}
