package Organizaciones;

public enum TipoEntidadJuridica {
    OSC {
        @Override
        public String getNombre() {
            return "OSC";
        }
    },
    MICRO {
        @Override
        public String getNombre() {
            return "Micro";
        }
    },
    PEQUENIA {
        @Override
        public String getNombre() {
            return "Pequeña";
        }
    },
    MEDIANA_TRAMO_1 {
        @Override
        public String getNombre() {
            return "Mediana tramo 1";
        }
    },
    MEDIANA_TRAMO_2 {
        @Override
        public String getNombre() {
            return "Mediana tramo 2";
        }
    };

    public abstract String getNombre();

    public int getVal() {
        return this.ordinal();
    }
}