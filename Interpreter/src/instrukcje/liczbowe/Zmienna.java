package instrukcje.liczbowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Zmienna implements Instrukcja {
    final String nazwa;

    Zmienna(String nazwa) {
        this.nazwa = nazwa;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        if (!zmienne.containsKey(nazwa)) {
            zmienne.put(this.nazwa, 0.0);
        }

        return zmienne.get(this.nazwa);
    }

    
    public String wypisz(String tabs, boolean print) {
        return tabs + this.nazwa;
    }
}
