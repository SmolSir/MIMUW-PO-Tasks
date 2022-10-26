package instrukcje;

import java.util.HashMap;

public interface Instrukcja {

    double oblicz(HashMap<String, Double> zmienne);

    String wypisz(String tabs, boolean print);
}
