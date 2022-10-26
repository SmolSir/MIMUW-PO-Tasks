package instrukcje.liczbowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class False implements Instrukcja {


    public double oblicz(HashMap<String, Double> zmienne) {
        return 0;
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + "0.0";
    }
}
