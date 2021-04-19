package OperacionDeEgresos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import OperacionDeEgresos.Item;
import org.hibernate.Criteria;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RepositorioProveedores implements WithGlobalEntityManager{

    public static RepositorioProveedores instancia = new RepositorioProveedores();

    public void agregar(Proveedor proveedor) {
        entityManager().persist(proveedor);
    }

    public List<Proveedor> listar() {
        return entityManager().createQuery("from Proveedor", Proveedor.class)
                .getResultList();
    }

    public List<Proveedor> todos() {
        List<Proveedor> lista = entityManager().createQuery("SELECT e FROM Proveedor e").getResultList();
        return lista;
    }


    public Proveedor buscar(long id){
        return entityManager().find(Proveedor.class, id);
    }
}