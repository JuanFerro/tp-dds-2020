package Usuario;

import Excepciones.WrongUserCredentialException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.security.MessageDigest;
import java.util.List;

public class RepositorioMensajes implements WithGlobalEntityManager {

    public static RepositorioMensajes instancia = new RepositorioMensajes();

    public void agregar(MensajeValidacionEgreso mensaje) {
        entityManager().persist(mensaje);
    }

    public List<MensajeValidacionEgreso> listar() {
        return entityManager().createQuery("from mensajevalidacionegreso", MensajeValidacionEgreso.class)
                .getResultList();
    }

    public MensajeValidacionEgreso buscar(long id){
        return entityManager().find(MensajeValidacionEgreso.class, id);
    }
}