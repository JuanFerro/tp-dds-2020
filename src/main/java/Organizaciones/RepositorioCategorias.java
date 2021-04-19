package Organizaciones;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioCategorias implements WithGlobalEntityManager{

    public static RepositorioCategorias instancia = new RepositorioCategorias();

    public void agregar(Categoria categoria) {
        entityManager().persist(categoria);
    }

    public List<Categoria> listar() {
        return entityManager().createQuery("from Categoria", Categoria.class)
                .getResultList();
    }

    public Categoria buscar(long id){
        return entityManager().find(Categoria.class, id);
    }
}
