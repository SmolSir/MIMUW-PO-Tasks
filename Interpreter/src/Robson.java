import com.squareup.moshi.Moshi;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

import instrukcje.*;
import instrukcje.arytmetyczne.*;
import instrukcje.blokowe.*;
import instrukcje.liczbowe.*;
import instrukcje.logiczne.*;
import wyjatki.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Robson {

    private static HashMap<String, Double> zmienne = new HashMap<>();
    private static Instrukcja program;
    private static String java;

    private static Moshi moshi = new Moshi.Builder()
            .add(
                    PolymorphicJsonAdapterFactory.of(Instrukcja.class, "typ")
                            .withSubtype(Blok.class, "Blok")
                            .withSubtype(True.class, "True")
                            .withSubtype(False.class, "False")
                            .withSubtype(Not.class, "Not")
                            .withSubtype(And.class, "And")
                            .withSubtype(Or.class, "Or")
                            .withSubtype(Mniejsze.class, "<")
                            .withSubtype(Wieksze.class, ">")
                            .withSubtype(MniejszeRowne.class, "<=")
                            .withSubtype(WiekszeRowne.class, ">=")
                            .withSubtype(Rowne.class, "==")
                            .withSubtype(If.class, "If")
                            .withSubtype(While.class, "While")
                            .withSubtype(Liczba.class, "Liczba")
                            .withSubtype(Zmienna.class, "Zmienna")
                            .withSubtype(Przypisanie.class, "Przypisanie")
                            .withSubtype(Plus.class, "Plus")
                            .withSubtype(Minus.class, "Minus")
                            .withSubtype(Razy.class, "Razy")
                            .withSubtype(Dzielenie.class, "Dzielenie")
            )
            .build();

    private static JsonAdapter<Instrukcja> jsonAdapter = moshi.adapter(Instrukcja.class);


    public Robson(){
    }


    public static void fromJSON(String filename) throws NieprawidlowyProgram {
        try {
            program = jsonAdapter.fromJson(Files.readString(Path.of(filename)));
        } catch (IOException e) {
            throw new NieprawidlowyProgram();
        }
    }


    public static void toJSON(String filename) {
        String new_filename = filename + ".json";
        File JSON = new File(new_filename);

        try {
            fromJSON(filename);
        } catch (NieprawidlowyProgram e) {
            new NieprawidlowyProgram();
        }

        try {
            JSON.createNewFile();
            Files.writeString(Path.of(new_filename), moshi.adapter(Instrukcja.class).indent("   ").toJson(program));
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas tworzenia pliku " + filename + ".json");
            System.exit(1);
        }
    }


    public static void toJava(String filename) {
        String new_filename = filename + ".java";
        zmienne.clear();

        try {
            fromJSON(filename);
        } catch (NieprawidlowyProgram e) {
            new NieprawidlowyProgram();
        }

        try {
            wykonaj();
        } catch (BladWykonania e) {
            new BladWykonania();
        }

        File JAVA = new File(new_filename);
        try {
            JAVA.createNewFile();
            java = "public class " + filename + " {\n\tpublic static void main(String args[]) {\n";

            for (String nazwa : zmienne.keySet()) {
                java += "\t\tdouble " + nazwa + " = 0.0;\n";
            }

            java += "\n";
            java += program.wypisz("\t\t", true);
            java += "\t}\n}\n";

            Files.writeString(Path.of(new_filename), java);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas tworzenia pliku " + filename + ".json");
            System.exit(1);
        }
    }


    public static double wykonaj() throws BladWykonania {
        return program.oblicz(zmienne);
    }
}
