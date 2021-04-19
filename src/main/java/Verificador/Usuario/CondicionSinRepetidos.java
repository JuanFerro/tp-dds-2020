package Verificador.Usuario;

import java.util.function.Function;

public class CondicionSinRepetidos extends CondicionDeCaracterPorNegativo {
    private final int repeticionesMaximas;

    public CondicionSinRepetidos(int repeticionesMaximas) {
        if (repeticionesMaximas < 2) repeticionesMaximas = 2; // Si es menor que 2 siempre tiraria la excepcion

        this.repeticionesMaximas = repeticionesMaximas;
    }

    public CondicionSinRepetidos() {
        this(3); // Por defecto 3
    }


    @Override
    protected Function<Integer, Boolean> condicionDeCaracter(String password) {
        return c -> caracterNoSeRepiteEn(password, c);
    }

    // auxiliar para llamar en verificar Repetidos
    protected boolean caracterNoSeRepiteEn(String password, int asciiCode) {
        char caracter = (char)asciiCode;
        String cadenaRepetida = new String(new char[this.repeticionesMaximas]).replace('\0', caracter); // A falta de String.repeat()
        return password.contains(cadenaRepetida);
    }

    protected String getMensajeExcepcion() {
        return "Los caracteres no se pueden repetir mas de " + this.repeticionesMaximas + " veces seguidas";
    }
}
