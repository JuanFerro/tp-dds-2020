package Usuario;

import OperacionDeEgresos.Egreso;
import Otros.PersistedEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class MensajeValidacionEgreso extends PersistedEntity {

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "egreso_id")
    private Egreso egreso;

    private boolean visto = false;
    private String contenido;

    public MensajeValidacionEgreso() {}

    public MensajeValidacionEgreso(String contenido, Egreso egreso) {
        this.contenido = contenido;
        this.egreso = egreso;
    }

    public String getContenido(){return contenido;}

    // aca irian los getters y setters

    public void marcarVisto() {
        visto = true;
    }

    public boolean getVisto() {return visto;}

    public Egreso getEgreso() {
        return egreso;
    }
}

