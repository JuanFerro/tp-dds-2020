package Verificador.Usuario;

import Verificador.Condicion;

import java.util.function.Function;

public abstract class CondicionDeCaracterPorPositivo extends Condicion<String> {

    protected boolean cumpleCondicion(String password) {
        Function<Integer, Boolean> miFuncion = this.condicionDeCaracter(password);
        return password.chars().anyMatch(miFuncion::apply);   // ch -> miFuncion.apply(ch)
    }

    protected abstract Function<Integer, Boolean> condicionDeCaracter(String password);
}
