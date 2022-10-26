package instrukcje.blokowe;

import instrukcje.Instrukcja;
import instrukcje.liczbowe.Przypisanie;

import java.util.HashMap;
import java.util.List;

public class Blok implements Instrukcja {
    final List<Instrukcja> instrukcje;


    Blok(List<Instrukcja> instrukcje) {
        this.instrukcje = instrukcje;
    }


    public double oblicz(HashMap<String, Double> zmienne) {
        if (this.instrukcje.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < this.instrukcje.size() - 1; i++) {
            this.instrukcje.get(i).oblicz(zmienne);
        }

        return this.instrukcje.get(this.instrukcje.size() - 1).oblicz(zmienne);
    }


    public String wypisz(String tabs, boolean print) {
        String java = new String();

        if (this.instrukcje.isEmpty()) {
            return tabs + "System.out.println(0.0);";
        }

        for (int i = 0; i < this.instrukcje.size() - 1; i++) {
            java += this.instrukcje.get(i).wypisz(tabs, false) + "\n";
        }

        Instrukcja instrukcja = this.instrukcje.get(this.instrukcje.size() - 1);
        if (instrukcja instanceof If || instrukcja instanceof While || instrukcja instanceof Przypisanie) {
            java += instrukcja.wypisz(tabs, print) + "\n";
        }
        else if (print) {
            java += tabs + "System.out.println(";
            java += instrukcja.wypisz("", false);
            java += ");\n";
        }
        else {
            java += instrukcja.wypisz(tabs, false) + "\n";
        }

        return java;
    }
}
