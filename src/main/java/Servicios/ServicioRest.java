package Servicios;

import Excepciones.BadStatusApiException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;

public class ServicioRest {
    private Client client;
    private static final String API_ML = "https://api.mercadolibre.com/";

    public ServicioRest () {
        client = new Client();
    }

    // REST
    public ClientResponse get(String pathUrl) {
        ClientResponse response = client
                .resource(API_ML)
                .path(pathUrl)
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        return response;
    }

    private void verificarStatusResponse(ClientResponse response) {
        Integer status = response.getStatus();
        if (!status.equals(200))
            throw new BadStatusApiException("La API devolvio un estado " + status.toString());
    }

    // UTILS JSON (GSON)

    String getJsonFromApiGet(String path) {
        ClientResponse response = get(path);
        return getJsonFromResponse(response);
    }

    String getJsonFromResponse(ClientResponse response) {
        verificarStatusResponse(response); // si no es 200 tira excepcion
        String json = response.getEntity(String.class);
        return json;
    }
}
