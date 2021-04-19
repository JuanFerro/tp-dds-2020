package Servicios;

import com.google.gson.internal.LinkedTreeMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ServicioUbicacionYMonedaTest {
    ServicioUbicacion servicio = mock(ServicioUbicacion.class);
    ServicioRest servicioRest = mock(ServicioRest.class);
    JsonParser jsonParser = mock(JsonParser.class);
    ClientResponse response = mock(ClientResponse.class);

    @Test
    public void parsePaises() {
        when(servicioRest.getJsonFromResponse(response)).thenReturn("[{\"id\":\"AR\",\"name\":\"Argentina\",\"locale\":\"es_AR\",\"currency_id\":\"ARS\"},{\"id\":\"AU\",\"name\":\"Australia\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"BO\",\"name\":\"Bolivia\",\"locale\":\"es_BO\",\"currency_id\":\"BOB\"},{\"id\":\"BR\",\"name\":\"Brasil\",\"locale\":\"pt_BR\",\"currency_id\":\"BRL\"},{\"id\":\"CA\",\"name\":\"Canada\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"CL\",\"name\":\"Chile\",\"locale\":\"es_CL\",\"currency_id\":\"CLP\"},{\"id\":\"CN\",\"name\":\"China\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"CO\",\"name\":\"Colombia\",\"locale\":\"es_CO\",\"currency_id\":\"COP\"},{\"id\":\"CR\",\"name\":\"Costa Rica\",\"locale\":\"es_CR\",\"currency_id\":\"CRC\"},{\"id\":\"CU\",\"name\":\"Cuba\",\"locale\":\"es_CU\",\"currency_id\":\"CUC\"},{\"id\":\"EC\",\"name\":\"Ecuador\",\"locale\":\"es_EC\",\"currency_id\":\"USD\"},{\"id\":\"SV\",\"name\":\"El Salvador\",\"locale\":\"es_SV\",\"currency_id\":\"USD\"},{\"id\":\"FR\",\"name\":\"France\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"DE\",\"name\":\"Germany\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"GB\",\"name\":\"Great Britain\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"GT\",\"name\":\"Guatemala\",\"locale\":\"es_GT\",\"currency_id\":\"GTQ\"},{\"id\":\"HN\",\"name\":\"Honduras\",\"locale\":\"es_HN\",\"currency_id\":\"HNL\"},{\"id\":\"HK\",\"name\":\"Hong Kong\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"IT\",\"name\":\"Italy\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"JP\",\"name\":\"Japan\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"MX\",\"name\":\"Mexico\",\"locale\":\"es_MX\",\"currency_id\":\"MXN\"},{\"id\":\"NI\",\"name\":\"Nicaragua\",\"locale\":\"es_NI\",\"currency_id\":\"NIO\"},{\"id\":\"PA\",\"name\":\"Panamá\",\"locale\":\"es_PA\",\"currency_id\":\"USD\"},{\"id\":\"PY\",\"name\":\"Paraguay\",\"locale\":\"es_PY\",\"currency_id\":\"PYG\"},{\"id\":\"PE\",\"name\":\"Peru\",\"locale\":\"es_PE\",\"currency_id\":\"PEN\"},{\"id\":\"PT\",\"name\":\"Portugal\",\"locale\":\"pt_PT\",\"currency_id\":\"EUR\"},{\"id\":\"PR\",\"name\":\"Puerto Rico\",\"locale\":\"es_PR\",\"currency_id\":\"USD\"},{\"id\":\"DO\",\"name\":\"República Dominicana\",\"locale\":\"es_DO\",\"currency_id\":\"DOP\"},{\"id\":\"KR\",\"name\":\"South Korea\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"ES\",\"name\":\"Spain\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"TR\",\"name\":\"Turkey\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"US\",\"name\":\"United States of America\",\"locale\":\"en_US\",\"currency_id\":\"USD\"},{\"id\":\"UY\",\"name\":\"Uruguay\",\"locale\":\"es_UY\",\"currency_id\":\"UYU\"},{\"id\":\"VE\",\"name\":\"Venezuela\",\"locale\":\"es_VE\",\"currency_id\":\"VES\"}]");
        String json = servicioRest.getJsonFromResponse(response);
        List<Map> map = jsonParser.parseJsonList(json);
        assertNotNull(map);
    }

    @Test
    public void parseProvincias() {
        when(servicioRest.getJsonFromResponse(response)).thenReturn("{\"id\":\"AR\",\"name\":\"Argentina\",\"locale\":\"es_AR\",\"currency_id\":\"ARS\",\"decimal_separator\":\",\",\"thousands_separator\":\".\",\"time_zone\":\"GMT-03:00\",\"geo_information\":{\"location\":{\"latitude\":-38.416096,\"longitude\":-63.616673}},\"states\":[{\"id\":\"AR-B\",\"name\":\"Buenos Aires\"},{\"id\":\"AR-C\",\"name\":\"Capital Federal\"},{\"id\":\"AR-K\",\"name\":\"Catamarca\"},{\"id\":\"AR-H\",\"name\":\"Chaco\"},{\"id\":\"AR-U\",\"name\":\"Chubut\"},{\"id\":\"AR-W\",\"name\":\"Corrientes\"},{\"id\":\"AR-X\",\"name\":\"Córdoba\"},{\"id\":\"AR-E\",\"name\":\"Entre Ríos\"},{\"id\":\"AR-P\",\"name\":\"Formosa\"},{\"id\":\"AR-Y\",\"name\":\"Jujuy\"},{\"id\":\"AR-L\",\"name\":\"La Pampa\"},{\"id\":\"AR-F\",\"name\":\"La Rioja\"},{\"id\":\"AR-M\",\"name\":\"Mendoza\"},{\"id\":\"AR-N\",\"name\":\"Misiones\"},{\"id\":\"AR-Q\",\"name\":\"Neuquén\"},{\"id\":\"AR-R\",\"name\":\"Río Negro\"},{\"id\":\"AR-A\",\"name\":\"Salta\"},{\"id\":\"AR-J\",\"name\":\"San Juan\"},{\"id\":\"AR-D\",\"name\":\"San Luis\"},{\"id\":\"AR-Z\",\"name\":\"Santa Cruz\"},{\"id\":\"AR-S\",\"name\":\"Santa Fe\"},{\"id\":\"AR-G\",\"name\":\"Santiago del Estero\"},{\"id\":\"AR-V\",\"name\":\"Tierra del Fuego\"},{\"id\":\"AR-T\",\"name\":\"Tucumán\"}]}");
        String json = servicioRest.getJsonFromResponse(response);
        Map<String, Object> map = jsonParser.parseJsonObject(json);
        assertNotNull(map);
    }

    @Test
    public void getPaises() {
        assertNotNull(servicio.paises());
    }

    @Test
    public void argentinaEsValido() {
        servicio.validarPais("Argentina");
    }

    @Test
    public void getProvinciasDeUruguay() {
        assertNotNull(servicio.provinciasDe("Uruguay"));
    }

    @Test
    public void laPampaSiExistia() {
        servicio.validarProvincia("La Pampa", "Argentina");
    }

    @Test
    public void getCiudadesDeChaco() {
        assertNotNull(servicio.ciudadesDe("Chaco", "Argentina"));
    }

    @Test
    public void bienvenidosALomasDeSavora() {
        servicio.validarCiudad("Lomas de Zamora", "Bs.As. G.B.A. Sur", "Argentina");
    }
}