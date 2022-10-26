package instrukcje.liczbowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class True implements Instrukcja {


    public double oblicz(HashMap<String, Double> zmienne) {
        return 1;
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + "1.0";
    }
}
