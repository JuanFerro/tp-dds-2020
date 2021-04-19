package OperacionDeEgresos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Embeddable
public class DocumentoComercial {

    @Column(nullable = true)
    @Enumerated
    private TipoDocumentoComercial tipo;

    @Column(nullable = true)
    private int numero;

    public DocumentoComercial () { }

    public DocumentoComercial (TipoDocumentoComercial tipo, int numero){
        this.tipo = tipo;
        this.numero= numero;
    }
}



