package instrukcje.liczbowe;

import instrukcje.Instrukcja;

import java.util.HashMap;

public class Przypisanie implements Instrukcja {
    private String nazwa;
    private Instrukcja wartosc;


    public Przypisanie(String nazwa, Instrukcja wartosc) {
        this.nazwa = nazwa;
        this.wartosc = wartosc;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        double nowa_wartosc = this.wartosc.oblicz(zmienne);

        zmienne.remove(this.nazwa);
        zmienne.put(this.nazwa, nowa_wartosc);

        return zmienne.get(this.nazwa);
    }


    public String wypisz(String tabs, boolean print) {
        String java = new String();

        java += tabs + this.nazwa + " = " + this.wartosc.wypisz("", print) + ";";

        if (print) {
            java += tabs + "System.out.println(";
            java += this.wartosc.wypisz("", false);
            java += ");\n";
        }

        return java;
    }
}
