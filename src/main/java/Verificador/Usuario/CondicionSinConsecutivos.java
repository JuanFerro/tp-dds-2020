package Verificador.Usuario;

import java.util.function.Function;

public class CondicionSinConsecutivos extends CondicionDeCaracterPorNegativo {
    private final int consecutivosMaximos;

    public CondicionSinConsecutivos(int consecutivosMaximos) {
        if (consecutivosMaximos < 2) consecutivosMaximos = 2; // Si es menor que 2 siempre tiraria la excepcion

        this.consecutivosMaximos = consecutivosMaximos;
    }

    public CondicionSinConsecutivos() {
        this(3); // Por defecto 3
    }

    @Override
    protected Function<Integer, Boolean> condicionDeCaracter(String password) {
        return c -> caracterTieneConsecutivosEn(password, c);
    }

    // auxiliar para llamar en verificarConsecutivos
    protected boolean caracterTieneConsecutivosEn(String password, int asciiCode) {
        char caracter = (char)asciiCode;

        return password.contains(this.cadenaConsecutiva(caracter, 1)) || password.contains(cadenaConsecutiva(caracter, -1));
    }

    // auxiliar para llamar en verificarConsecutivos
    protected String cadenaConsecutiva(char caracter, int sentido) { // 1 creciente, -1 decreciente, ver de cambiar :D
        String cadena = "";
        for (int i = 0; i < this.consecutivosMaximos; i++) {
            cadena += Character.toString(caracter);
            caracter += sentido;
        }

        return cadena;
    }

    protected String getMensajeExcepcion() {
        return "No puede haber mas de " + this.consecutivosMaximos + " caracteres consecutivos";
    }
}
