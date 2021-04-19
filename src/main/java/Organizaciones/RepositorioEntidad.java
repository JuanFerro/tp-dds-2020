package Organizaciones;

import java.util.List;

import OperacionDeEgresos.Egreso;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEntidad implements WithGlobalEntityManager{

    public static RepositorioEntidad instancia = new RepositorioEntidad();

    public void agregar(Entidad entidad) {
        entityManager().persist(entidad);
    }

    public List<Entidad> listar() {
        return entityManager().createQuery("from Entidad", Entidad.class)
                .getResultList();
    }

    public Entidad buscar(long id){
        return entityManager().find(Entidad.class, id);
    }

    public Entidad entidadQueTieneAlEgreso (Egreso egreso) {
        return this.listar().stream().filter(entidad -> entidad.getOperacionesDeEgresos().contains(egreso)).findAny().orElse(null);
    }
}
