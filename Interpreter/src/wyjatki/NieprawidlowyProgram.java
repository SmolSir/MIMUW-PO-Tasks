package wyjatki;

public class NieprawidlowyProgram extends Exception {

    public NieprawidlowyProgram() {
        System.out.println("NIEPRAWIDŁOWY PROGRAM!");
        System.exit(1);
    }
}
