package instrukcje.liczbowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Liczba implements Instrukcja {
    final double wartosc;


    Liczba(double wartosc) {
        this.wartosc = wartosc;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        return this.wartosc;
    }


    public String wypisz(String tabs, boolean print) {
        return tabs + String.valueOf(this.wartosc);
    }
}
