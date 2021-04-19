package Verificador.Usuario;

public abstract class CondicionDeCaracterPorNegativo extends CondicionDeCaracterPorPositivo {

    @Override
    protected boolean cumpleCondicion(String password) {
        return !super.cumpleCondicion(password);
    }
}
