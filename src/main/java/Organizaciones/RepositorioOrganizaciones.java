package Organizaciones;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioOrganizaciones implements WithGlobalEntityManager{

    public static RepositorioOrganizaciones instancia = new RepositorioOrganizaciones();

    public void agregar(Organizacion organizacion) {
        entityManager().persist(organizacion);
    }

    public List<Organizacion> listar() {
        return entityManager().createQuery("from Organizacion", Organizacion.class)
                .getResultList();
    }

    public Organizacion buscar(long id){
        return entityManager().find(Organizacion.class, id);
    }
}
