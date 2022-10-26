package instrukcje.logiczne;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Wieksze implements Instrukcja {
    private Instrukcja argument1;
    private Instrukcja argument2;


    public Wieksze(Instrukcja argument1, Instrukcja argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        return (this.argument1.oblicz(zmienne) > this.argument2.oblicz(zmienne) ? 1 : 0);
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + this.argument1.wypisz("", false) + " > " + this.argument2.wypisz("", false);
    }
}
