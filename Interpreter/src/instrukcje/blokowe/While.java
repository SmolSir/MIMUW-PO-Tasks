package instrukcje.blokowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class While implements Instrukcja {
    private Instrukcja warunek;
    private Instrukcja blok;


    public While(Instrukcja warunek, Instrukcja blok) {
        this.warunek = warunek;
        this.blok = blok;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        int a = 0;

        while (this.warunek.oblicz(zmienne) != 0 && a < 10) {
            this.blok.oblicz(zmienne);
            a++;
        }

        return 0.0;
    }

    public String wypisz(String tabs, boolean print) {
        String java = new String();

        java += tabs + "while (" + this.warunek.wypisz("", false) + ") {\n";
        java += blok.wypisz(tabs + "\t", false) + tabs + "}";

        if (print) {
            java += tabs + "System.out.println(0.0);\n";
        }

        return java;
    }
}
