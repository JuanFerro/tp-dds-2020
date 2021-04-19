package OperacionDeEgresos;
import Usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.*;

public  class RepositorioEgresos implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    private static RepositorioEgresos uniqueInstance = new RepositorioEgresos(); // Singleton

    public static RepositorioEgresos instance() {       // Singleton
        return uniqueInstance;
    }

    public List<Egreso> todos() {
        List<Egreso> lista = entityManager().createQuery("SELECT e FROM Egreso e").getResultList();
        return lista;
    }

    public List<Egreso> listar() {
        return entityManager().createQuery("from Egreso", Egreso.class)
                .getResultList();
    }

    public Egreso buscar(long id){
        return entityManager().find(Egreso.class, id);
    }

    public List<Egreso> validados() {
        return todos().stream().filter(e -> e.estaValidado()).collect(Collectors.toList());
    }
    public List<Egreso> pendientesSinValidar() {
        return todos().stream().filter(e -> !e.estaValidado()).collect(Collectors.toList());
    }

    public void validarLosPendientes() {
        withTransaction(() -> {
            pendientesSinValidar().forEach(egreso -> egreso.validar());
        });
    }

    public Egreso addEgreso(Egreso unEgreso) {
        entityManager().persist(unEgreso);
        return unEgreso;
    }

    public Egreso egresoConPresupuesto (Presupuesto unPresupuesto) { // Consigo que egreso posee al presupuesto pasado
        return todos().stream().filter(UnEgreso -> UnEgreso.elEgresoTieneElPresupuesto(unPresupuesto)).findAny().orElse(null);
    }

    public boolean algunEgresoTieneElItem(Item item){
        return todos().stream().anyMatch(unEgreso -> unEgreso.elEgresoTieneElItem(item));
    }
}
