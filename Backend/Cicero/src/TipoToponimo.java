public enum TipoToponimo {
    ORONIMO, POLEONIMO, CORONIMO, NESONIMO, LIMNONIMO, IDRONIMO, ODONIMO;

    public void stampaId () {
        System.out.println("ORONIMO: "+ORONIMO.ordinal());
        System.out.println("POLEONIMO: "+POLEONIMO.ordinal());
       System.out.println("CORONIMO: "+CORONIMO.ordinal());
       System.out.println("NESONIMO: "+NESONIMO.ordinal());
       System.out.println("LIMNONIMO: "+LIMNONIMO.ordinal());
       System.out.println ("IDRONIMO: "+IDRONIMO.ordinal());
       System.out.println("ODONIMO: "+ODONIMO.ordinal());
    }

    public TipoToponimo getTipo (int i) {
        if(i == ORONIMO.ordinal()) {
            return ORONIMO;
        }
        else if (i == POLEONIMO.ordinal()) {
            return POLEONIMO;
        }
        else if (i == CORONIMO.ordinal()) {
            return CORONIMO;
        }
        else if (i == NESONIMO.ordinal()) {
            return NESONIMO;
        }
        else if (i == LIMNONIMO.ordinal()) {
            return LIMNONIMO;
        }
        else if (i == IDRONIMO.ordinal()) {
            return IDRONIMO;
        }
        else  return ODONIMO;
    }
}
