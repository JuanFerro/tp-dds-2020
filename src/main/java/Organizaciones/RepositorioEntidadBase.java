package Organizaciones;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RepositorioEntidadBase implements WithGlobalEntityManager{

    public static RepositorioEntidadBase instancia = new RepositorioEntidadBase();

    public void agregar(EntidadBase entidadBase) {
        entityManager().persist(entidadBase);
    }

    public List<EntidadBase> listar() {
        return entityManager().createQuery("from EntidadBase", EntidadBase.class)
                .getResultList();
    }

    public EntidadBase buscar(long id){
        return entityManager().find(EntidadBase.class, id);
    }
}
