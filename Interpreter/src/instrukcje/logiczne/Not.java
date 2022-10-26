package instrukcje.logiczne;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Not implements Instrukcja {
    private Instrukcja argument;


    public Not(Instrukcja argument) {
        this.argument = argument;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        return (this.argument.oblicz(zmienne) == 0 ? 1 : 0);
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + "!(" + this.argument.wypisz("", false) + ")";
    }
}
