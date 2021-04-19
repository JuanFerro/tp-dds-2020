package Verificador.Usuario;

import Verificador.Condicion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CondicionNoEstaEnElArchivo extends Condicion<String> {
    String currentDir = Paths.get(".").toAbsolutePath().toString();  // Consigo la carpeta del proyecto
    String fileName = "src/main/java/docs/commonpsws.txt";


    @Override
    protected boolean cumpleCondicion(String password) {
        // El try Catch es para pasar de la excepcion chequeada a una no chequeadas
        try{
            Stream<String> passwordsStream = Files.lines(Paths.get(currentDir, fileName));
            List<String> passwordsList;
            passwordsList = passwordsStream.collect(Collectors.toList());

            return !passwordsList.contains(password);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected String getMensajeExcepcion() {
        return "La contraseña es muy debil";
    }
}
