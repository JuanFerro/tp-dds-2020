package Verificador.Usuario;
import Verificador.Condicion;

public class CondicionLongitudMinima extends Condicion<String> {
    private int longitudMinima;

    public CondicionLongitudMinima(int longitudMinima) {
        this.longitudMinima = longitudMinima;
    }

    public CondicionLongitudMinima() {
        this(8); // Por defecto son 8
    }



    @Override
    protected boolean cumpleCondicion(String password) {
        return password.length() >= this.longitudMinima;
    }

    protected String getMensajeExcepcion() {
        return "La contraseña debe tener como minimo " + this.longitudMinima + " caracteres";
    }
}
