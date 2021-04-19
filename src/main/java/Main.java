import OperacionDeEgresos.RepositorioEgresos;

public class Main {
    public static void main(String[] args) {
        switch (args[0]) {
            case "server":
                Server.Server.main(args);
                break;
            case "validarEgresos":
                System.out.println("Se van a validar todos los egresos pendientes");
                RepositorioEgresos.instance().validarLosPendientes();
                System.out.println("Se validaron todos los egresos, adios");
                break;
        }
        return;
    }
}
