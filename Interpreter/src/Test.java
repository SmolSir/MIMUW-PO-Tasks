import wyjatki.BladWykonania;
import wyjatki.NieprawidlowyProgram;

public class Test extends Robson {

    private static Robson robson = new Robson();

    private static void test(String input) {
        try {
            robson.fromJSON(input);
        } catch (NieprawidlowyProgram e) {
            new NieprawidlowyProgram();
        }

        try {
            System.out.println(input + ":\n" + wykonaj() + "\n");
        } catch (BladWykonania e) {
            new BladWykonania();
        }

        toJSON(input);
        toJava(input);
    }


    public static void main(String[] args) {
        test("Example");
        test("Fibonacci");
        test("Euklides");
    }
}
