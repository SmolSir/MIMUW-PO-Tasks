package instrukcje.arytmetyczne;

import instrukcje.Instrukcja;
import wyjatki.BladWykonania;

import java.util.HashMap;

public class Dzielenie implements Instrukcja {
    final Instrukcja argument1;
    final Instrukcja argument2;


    Dzielenie(Instrukcja argument1, Instrukcja argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        if (this.argument2.oblicz(zmienne) == 0) {
            new BladWykonania();
            System.exit(1);
        }

        return this.argument1.oblicz(zmienne) / this.argument2.oblicz(zmienne);
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + "(" + this.argument1.wypisz("", false) + ") / (" + this.argument2.wypisz("", false) + ")";
    }
}
