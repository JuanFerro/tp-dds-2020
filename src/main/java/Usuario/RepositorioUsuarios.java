package Usuario;

import java.security.MessageDigest;
import java.util.List;

import Excepciones.WrongUserCredentialException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioUsuarios implements WithGlobalEntityManager{

    public static RepositorioUsuarios instancia = new RepositorioUsuarios();

    public void agregar(Usuario usuario) {
        entityManager().persist(usuario);
    }

    public List<Usuario> listar() {
        return entityManager().createQuery("from Usuario", Usuario.class)
                .getResultList();
    }

    public Usuario buscar(long id){
        return entityManager().find(Usuario.class, id);
    }

    public Usuario validarCredenciales(String username, String password) {
        Usuario usuario = entityManager()
                .createQuery("from Usuario u WHERE username = :username", Usuario.class)
                .setParameter("username", username)
                .getSingleResult();

        String passwordHasheada = hashear(password);

        if (!passwordHasheada.equals(usuario.getPassword())) {
            throw new WrongUserCredentialException(passwordHasheada);
        }

        return usuario;
    }

    public String hashear (String psw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        byte[] digest = md.digest(psw.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
