package com.zdravstvo.demo.model;

public enum SpecijalnostDoktora {
    STOMATOLOG,
    GINEKOLOG,
    OPSTA_PRAKSA;

    public static SpecijalnostDoktora returnSpecijalnost(String specijalnost){
        if(specijalnost.equalsIgnoreCase("STOMATOLOG")){
            return SpecijalnostDoktora.STOMATOLOG;
        } else if (specijalnost.equalsIgnoreCase("GINEKOLOG")) {
            return SpecijalnostDoktora.GINEKOLOG;
        } else {
            return SpecijalnostDoktora.OPSTA_PRAKSA;
        }
    }

    public static String returnSpecijalnostString(SpecijalnostDoktora specijanost){
        if(specijanost.equals(SpecijalnostDoktora.STOMATOLOG)){
            return "STOMATOLOG";
        } else if (specijanost.equals(SpecijalnostDoktora.GINEKOLOG)) {
            return "GINEKOLOG";
        } else {
            return "OPSTA PRAKSA";
        }
    }

}
