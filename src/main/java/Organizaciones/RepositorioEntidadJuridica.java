package Organizaciones;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioEntidadJuridica implements WithGlobalEntityManager{

    public static RepositorioEntidadJuridica instancia = new RepositorioEntidadJuridica();

    public void agregar(EntidadJuridica entidadJuridica) {
        entityManager().persist(entidadJuridica);
    }

    public List<EntidadJuridica> listar() {
        return entityManager().createQuery("from EntidadJuridica", EntidadJuridica.class)
                .getResultList();
    }

    public EntidadJuridica buscar(long id){
        return entityManager().find(EntidadJuridica.class, id);
    }

    public EntidadJuridica entidadJuridicaQueTiene(EntidadBase entidadBase) {
        return listar().stream().filter(entidadJuridica -> entidadJuridica.getEntidadesBase().contains(entidadBase)).findAny().orElse(null);
    }
}
