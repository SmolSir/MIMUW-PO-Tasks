package wyjatki;

public class BladWykonania extends Exception {

    public BladWykonania() {
        System.out.println("BŁĄD WYKONANIA!");
        System.exit(1);
    }
}
