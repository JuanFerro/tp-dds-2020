package OperacionDeEgresos;

public enum TipoMedioDePago
{
    TARJETA_CREDITO {
        @Override
        public String getNombre() {
            return "Tarjeta de Credito";
        }
    },
    TARJETA_DEBITO {
        @Override
        public String getNombre() {
            return "Tarjeta de Debito";
        }
    },
    EFECTIVO {
        @Override
        public String getNombre() {
            return "Efectivo";
        }
    },
    CAJERO_AUTOMATICO {
        @Override
        public String getNombre() {
            return "Cajero Automático";
        }
    },
    DINERO_EN_CUENTA {
        @Override
        public String getNombre() {
            return "Dinero en Cuenta";
        }
    };

    public abstract String getNombre();

    public int getVal() {
        return this.ordinal();
    }
}


