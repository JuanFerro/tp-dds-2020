package OperacionDeEgresos;

import java.util.List;
import java.util.Map;

import OperacionDeEgresos.Item;
import org.hibernate.Criteria;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RepositorioItem implements WithGlobalEntityManager{

    public static RepositorioItem instancia = new RepositorioItem();

    public void agregar(Item item) {
        entityManager().persist(item);
    }

    public List<Item> listar() {
        return entityManager().createQuery("from Item", Item.class)
                .getResultList();
    }

    public Item buscar(long id){
        return entityManager().find(Item.class, id);
    }
}
