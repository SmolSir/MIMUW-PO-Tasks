package instrukcje.blokowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class If implements Instrukcja {
    private Instrukcja warunek;
    private Instrukcja blok_prawda;
    private Instrukcja blok_falsz;


    public If(Instrukcja warunek, Instrukcja blok_prawda, Instrukcja blok_falsz) {
        this.warunek = warunek;
        this.blok_prawda = blok_prawda;
        this.blok_falsz = blok_falsz;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        if (this.warunek.oblicz(zmienne) == 0) {
            return (this.blok_falsz == null ? 0 : this.blok_falsz.oblicz(zmienne));
        }

        return this.blok_prawda.oblicz(zmienne);
    }

    public String wypisz(String tabs, boolean print) {
        String java = new String();

        java += tabs + "if (" + this.warunek.wypisz("", false) + ") {\n";
        java += this.blok_prawda.wypisz(tabs + "\t", print) + tabs + "}";

        if (this.blok_falsz != null) {
            java += "\n" + tabs + "else {\n";
            java += this.blok_falsz.wypisz(tabs + "\t", print) + tabs + "}";
        }

        return java;
    }
}
