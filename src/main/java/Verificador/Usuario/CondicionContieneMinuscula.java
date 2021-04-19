package Verificador.Usuario;

import java.util.function.Function;

public class CondicionContieneMinuscula extends CondicionDeCaracterPorPositivo {
    @Override
    protected Function<Integer, Boolean> condicionDeCaracter(String password) {
        return ch -> Character.isLowerCase(ch);
    }

    @Override
    protected String getMensajeExcepcion() {
        return "Debe contener por lo menos una minuscula";
    }
}
