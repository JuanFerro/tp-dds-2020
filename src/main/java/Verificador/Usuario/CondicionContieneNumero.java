package Verificador.Usuario;

import java.util.function.Function;

public class CondicionContieneNumero extends CondicionDeCaracterPorPositivo {
    @Override
    protected Function<Integer, Boolean> condicionDeCaracter(String password) {
        return ch -> Character.isDigit(ch);
    }

    @Override
    protected String getMensajeExcepcion() {
        return "Debe contener por lo menos un numero";
    }
}
