package OperacionDeEgresos;


import Fixtures.Fixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DireccionPostalTest {

    private Fixture fixture;
    private DireccionPostal laCasaDeLuis;

    @Before
    public void cargarDireccionPostal(){
        fixture = new Fixture();
        laCasaDeLuis = fixture.getDireccionPostal();
    }

    @Test
    public void argentinaTienePesos() {
        assertEquals("peso", laCasaDeLuis.monedaSegunElPais());
    }
}