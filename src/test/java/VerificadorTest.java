import Excepciones.InvalidConditionException;
import Verificador.Usuario.*;
import org.junit.Test;

public class VerificadorTest {

    // analisis de caracter en mayuscula
    @Test
    public void crearPasswordConMayuscula() {
        CondicionContieneMayuscula instancia = new CondicionContieneMayuscula();
        instancia.verificar("Hola");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordSinMayuscula() {
        CondicionLongitudMinima instancia = new CondicionLongitudMinima();
        instancia.verificar("hola");
    }

    // analisis de caracter en minuscula
    @Test
    public void crearPasswordConMinuscula() {
        CondicionContieneMinuscula instancia = new CondicionContieneMinuscula();
        instancia.verificar("pROBANDO");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordSinMinuscula() {
        CondicionContieneMinuscula instancia = new CondicionContieneMinuscula();
        instancia.verificar("PROBANDO");
    }

    // analisis de digito
    @Test
    public void crearPasswordConNumero() {
        CondicionContieneNumero instancia = new CondicionContieneNumero();
        instancia.verificar("r4pido");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordSinNumero() {
        CondicionContieneNumero instancia = new CondicionContieneNumero();
        instancia.verificar("rapido");
    }

    // analisis de longitud
    @Test
    public void crearPasswordConLongitudMayorA8() {
        CondicionLongitudMinima instancia = new CondicionLongitudMinima();
        instancia.verificar("MET4LLICA");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordConLongitudMenorA8() {
        CondicionLongitudMinima instancia = new CondicionLongitudMinima();
        instancia.verificar("1g5yH");
    }

    // analisis de caracteres consecutivos
    @Test
    public void crearPasswordSinCaracteresConsecutivos() {
        CondicionSinConsecutivos instancia = new CondicionSinConsecutivos();
        instancia.verificar("74nfivmr");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordConCaracteresConsecutivos() {
        CondicionSinConsecutivos instancia = new CondicionSinConsecutivos();
        instancia.verificar("abcde89i");
    }

    // analisis de caracteres repetidos
    @Test
    public void crearPasswordSinCaracteresRepetidos() {
        CondicionSinRepetidos instancia = new CondicionSinRepetidos();
        instancia.verificar("265nfjc");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordConCaracteresRepetidos() {
        CondicionSinRepetidos instancia = new CondicionSinRepetidos();
        instancia.verificar("6666ijtghkkkk");
    }

    // analisis de contraseña facil
    @Test
    public void crearPasswordDificil() {
        CondicionNoEstaEnElArchivo instancia = new CondicionNoEstaEnElArchivo();
        instancia.verificar("hend78567asdc");
    }
    @Test(expected = InvalidConditionException.class)
    public void crearPasswordFacil() {
        CondicionNoEstaEnElArchivo instancia = new CondicionNoEstaEnElArchivo();
        instancia.verificar("1234");
    }
}