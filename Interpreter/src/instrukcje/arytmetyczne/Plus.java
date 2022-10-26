package instrukcje.arytmetyczne;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Plus implements Instrukcja {
    final Instrukcja argument1;
    final Instrukcja argument2;


    Plus(Instrukcja argument1, Instrukcja argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        return this.argument1.oblicz(zmienne) + this.argument2.oblicz(zmienne);
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + this.argument1.wypisz(tabs, false) + " + " + this.argument2.wypisz(tabs, false);
    }
}
