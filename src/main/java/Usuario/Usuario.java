package Usuario;

import Organizaciones.Organizacion;
import Excepciones.InvalidConditionException;
import Otros.PersistedEntity;
import Verificador.Usuario.*;
import Verificador.Verificador;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Usuario extends PersistedEntity {

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private TipoUsuario tipoUsuario;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private List<MensajeValidacionEgreso> bandejaDeMensajes = new ArrayList<>();


    public Usuario () {}

    public Usuario(String username, String password, String passwordRe, TipoUsuario tipoUsuario, Organizacion organizacion) {

        if (!password.equals(passwordRe)) {
            throw new InvalidConditionException("Las contraseñas no coinciden");
        }

        // Verifico las condiciones
        Verificador verificador = this.verificador();
        verificador.verificarCondiciones(password);

        this.username = username;
        this.tipoUsuario = tipoUsuario;
        setPassword(password);
        this.organizacion = organizacion;

    }

    private Verificador verificador () {
        Verificador verificador = new Verificador();

        verificador.agregarCondicion(new CondicionNoEstaEnElArchivo());
        verificador.agregarCondicion(new CondicionLongitudMinima());
        verificador.agregarCondicion(new CondicionSinRepetidos());
        verificador.agregarCondicion(new CondicionSinConsecutivos());
        verificador.agregarCondicion(new CondicionContieneMayuscula());
        verificador.agregarCondicion(new CondicionContieneMinuscula());
        verificador.agregarCondicion(new CondicionContieneNumero());

        return verificador;
    }

    public boolean esValido(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void notificarValidacion(MensajeValidacionEgreso message) {
        bandejaDeMensajes.add(message);
    }

    public List<MensajeValidacionEgreso> getBandejaDeMensajes() { return bandejaDeMensajes; }

    public List<MensajeValidacionEgreso> getMensajesNoVistos() {
        return bandejaDeMensajes.stream().filter(mje -> !mje.getVisto()).collect(Collectors.toList());
    }

    public boolean bandejaDeUsuarioContieneMensaje(String message) {
        return bandejaDeMensajes.stream().anyMatch(mje -> mje.getContenido().equals(message));
    }

    public Organizacion organizacion() {
        return organizacion;
    }

    public String getPassword () {
        return this.password;
    }

    public void setPassword (String password) {
        this.password = RepositorioUsuarios.instancia.hashear(password);
    }

    public String getUsername() {return username; }

}
