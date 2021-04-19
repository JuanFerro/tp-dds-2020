import Excepciones.InvalidConditionException;
import org.junit.Test;
import static org.junit.Assert.*;
import Usuario.*;
import Organizaciones.Organizacion;

public class UsuarioTest {

    // Crear usuario y verificarContrasenia

    @Test
    public void crearUsuarioConContraseniaPerfecta () {
        Usuario mou = new Usuario("Supermou", "S5mjp1VT", "S5mjp1VT", TipoUsuario.ESTANDAR, new Organizacion());
    }

    @Test(expected = InvalidConditionException.class)
    public void crearUsuarioConCaracteresRepetidosEnLaContrasenia () {
        try
        {
            Usuario nicky = new Usuario("leNick", "s555mjp1vt", "s555mjp1vt", TipoUsuario.ESTANDAR, new Organizacion());
        }
        catch(InvalidConditionException exception)
        {
            String message = "Los caracteres no se pueden repetir mas de 3 veces seguidas";
            assertEquals(message, exception.getMessage());
            throw exception;
        }
        fail("Tiro mal la excepcion");
    }

    @Test(expected = InvalidConditionException.class)
    public void crearUsuarioConSoloSimbolosEnLaContrasenia () {
        try
        {
            Usuario nico = new Usuario("leNick2", "@*/*//*@", "@*/*//*@", TipoUsuario.ESTANDAR, new Organizacion());
        }
        catch(InvalidConditionException exception)
        {
            String message = "Debe contener por lo menos una mayuscula";
            assertEquals(message, exception.getMessage());
            throw exception;
        }
        fail("Tiro mal la excepcion");
    }

    @Test
    public void contraseniaHasheada () {
        String text = "asdda";
        String textHashed = RepositorioUsuarios.instancia.hashear(text);
        String textHashed2 = RepositorioUsuarios.instancia.hashear(text);

        assertEquals(textHashed, textHashed2);
    }
}
