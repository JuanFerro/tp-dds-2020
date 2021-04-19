package Organizaciones.Reglas;

import Organizaciones.Entidad;
import Otros.PersistedEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.INTEGER)
public abstract class ReglaDeNegocio extends PersistedEntity {

    @Enumerated
    private TipoDeRegla tipoDeRegla;

    public ReglaDeNegocio() {}

    public ReglaDeNegocio(TipoDeRegla tipoDeRegla){
        this.tipoDeRegla = tipoDeRegla;
    }

    public TipoDeRegla getTipoRegla(){
        return tipoDeRegla;
    }

    public abstract void permite(Entidad entidad);

}

