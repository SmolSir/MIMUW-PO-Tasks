package zad1.utils;

import zad1.Symulacja;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Wczytywanie extends Symulacja {

    private final String[] parametry = {
            "ile_tur",
            "pocz_ile_robów",
            "pocz_program",
            "pocz_energia",
            "ile_daje_jedzenie",
            "ile_rośnie_jedzenie",
            "koszt_tury",
            "pr_powielenia",
            "ułamek_energii_rodzica",
            "limit_powielania",
            "co_ile_wypisz",
            "spis_instr",
            "pr_usunięcia_instr",
            "pr_dodania_instr",
            "pr_zmiany_instr"
    };
    private final int ile_parametrów = parametry.length;
    private final boolean[] wczytano_parametr = new boolean[ile_parametrów];
    private char[] spis_instr_tmp;
    private char[] pocz_program_tmp;

    public Wczytywanie() {
    }

    private void sprawdź_wiersz(char[] wiersz) {
        for (char c : wiersz) {
            if (c != ' ' && c != 'x') {
                System.out.println("NIEDOZWOLONY ZNAK W OPISIE PLANSZY");
                System.exit(1);
            }
        }
    }

    private void sprawdź_długości(ArrayList<Integer>długości) {
        int poprzedni = długości.get(0);
        długości.remove(0);
        for (int długość : długości) {
            if (poprzedni != długość) {
                System.out.println("NIEPOPRAWNA PLANSZA");
                System.exit(1);
            }
            poprzedni = długość;
        }
    }

    private void sprawdź_planszę(String plik) {
        ArrayList<Integer>długość_wiersza = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(plik));
            rozmiar_planszy_y = 0;
            while (scanner.hasNextLine()) {
                String wiersz = scanner.nextLine();
                sprawdź_wiersz(wiersz.toCharArray());
                długość_wiersza.add(wiersz.length());
                rozmiar_planszy_y++;
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        if (długość_wiersza.isEmpty()) {
            System.out.println("PUSTA PLANSZA");
            System.exit(1);
        }
        rozmiar_planszy_x = długość_wiersza.get(0);
        sprawdź_długości(długość_wiersza);
    }

    private void wczytaj_planszę(String plik) {
        try {
            Scanner scanner = new Scanner(new File(plik));
            for (int y = rozmiar_planszy_y - 1; y >= 0; y--) {
                char[] wiersz = scanner.nextLine().toCharArray();
                for (int x = 0; x < rozmiar_planszy_x; x++) {
                    plansza.pole[x][y] = new Pole(wiersz[x]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private String typ(String nazwa) {
        if (
                nazwa.equals("ile_tur")                 ||
                nazwa.equals("pocz_ile_robów")          ||
                nazwa.equals("pocz_energia")            ||
                nazwa.equals("ile_daje_jedzenie")       ||
                nazwa.equals("ile_rośnie_jedzenie")     ||
                nazwa.equals("koszt_tury")              ||
                nazwa.equals("limit_powielania")        ||
                nazwa.equals("co_ile_wypisz")
        ) {
            return "int";
        }
        if (
                nazwa.equals("pr_powielenia")           ||
                nazwa.equals("ułamek_energii_rodzica")  ||
                nazwa.equals("pr_usunięcia_instr")      ||
                nazwa.equals("pr_dodania_instr")        ||
                nazwa.equals("pr_zmiany_instr")
        ) {
            return "double";
        }
        return nazwa;
    }

    private void sprawdź_instrukcje(char[] instr) {
        char[] dozwolone_instrukcje = {'l', 'p', 'i', 'w', 'j'};
        for (char i : instr) {
            boolean poprawna_instrukcja = false;
            for (char d : dozwolone_instrukcje) {
                if (i == d) {
                    poprawna_instrukcja = true;
                    break;
                }
            }
            if (!poprawna_instrukcja) {
                System.out.println("NIEDOZWOLONA INSTRUKCJA");
                System.exit(1);
            }
        }
    }

    private void sprawdź_pocz_prog_i_spis_instr(char[] prog, char[] spis) {
        for (char p : prog) {
            boolean dozwolony = false;
            for (char s : spis) {
                if (p == s) {
                    dozwolony = true;
                    break;
                }
            }
            if (!dozwolony) {
                System.out.println("NIEDOZWOLONA INSTRUKCJA W PROGRAMIE");
                System.exit(1);
            }
        }
    }

    private void sprawdź_nazwę_i_wartość(String nazwa, Scanner słowa) {
        boolean poprawna_nazwa = false;
        for (int i = 0; i < ile_parametrów; i++) {
            if (!parametry[i].equals(nazwa)) continue;
            poprawna_nazwa = true;

            if (wczytano_parametr[i]) {
                System.out.println("POWTÓRZONO PARAMETR " + nazwa);
                System.exit(1);
            }
            wczytano_parametr[i] = true;

            if (!słowa.hasNext()) {
                System.out.println("BRAK WARTOŚCI PARAMETRU " + nazwa);
                System.exit(1);
            }

            try {
                switch (typ(parametry[i])) {
                    case "int" -> słowa.nextInt();
                    case "pocz_program" -> pocz_program_tmp = słowa.next().toCharArray();
                    case "spis_instr" -> {
                        spis_instr_tmp = słowa.next().toCharArray();
                        sprawdź_instrukcje(spis_instr_tmp);
                    }
                    case "double" -> słowa.nextDouble();
                }

            } catch (InputMismatchException e) {
                System.out.println("NIEPOPRAWNY TYP PARAMETRU " + nazwa);
                System.exit(1);
            }
        }
        if (!poprawna_nazwa) {
            System.out.println("NIEPOPRAWNA NAZWA PARAMETRU " + nazwa);
            System.exit(1);
        }
    }

    private void sprawdź_parametry(String plik) {
        try {
            Scanner scanner = new Scanner(new File(plik));

            for (int i = 0; i < ile_parametrów; i++) {
                if (!scanner.hasNextLine()) {
                    System.out.println("ZA MAŁO PARAMETRÓW");
                    System.exit(1);
                }

                String wiersz = scanner.nextLine();
                if (wiersz.isBlank()) {
                    System.out.println("BRAK PARAMETRU");
                    System.exit(1);
                }

                Scanner słowa = new Scanner(wiersz).useDelimiter(" ");
                String nazwa_parametru = słowa.next();
                sprawdź_nazwę_i_wartość(nazwa_parametru, słowa);
            }
            sprawdź_pocz_prog_i_spis_instr(pocz_program_tmp, spis_instr_tmp);
            scanner.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private int sprawdź_zakres_wartości_int(String param, int wartość) {
        if ((param.equals("co_ile_wypisz") && wartość < 1) || (!param.equals("co_ile_wypisz") && wartość < 0)) {
            System.out.println("PARAMETR SPOZA ZAKRESU");
            System.exit(1);
        }
        return wartość;
    }

    private double sprawdź_zakres_wartości_double(double wartość) {
        if (wartość < 0 || 1 < wartość) {
            System.out.println("PARAMETR SPOZA ZAKRESU");
            System.exit(1);
        }
        return wartość;
    }

    private ArrayList<Character> to_ArrayList(char[] chars) {
        ArrayList<Character> arrayList = new ArrayList<>();
        for (char c : chars) {
            arrayList.add(c);
        }
        return arrayList;
    }

    private void wczytaj_wartość(String param, Scanner wartość) {
        switch (param) {
            case "ile_tur" -> ile_tur = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "pocz_ile_robów" -> pocz_ile_robów = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "pocz_program" -> pocz_program = to_ArrayList(wartość.next().toCharArray());
            case "pocz_energia" -> pocz_energia = wartość.nextInt();
            case "ile_daje_jedzenie" -> ile_daje_jedzenie = wartość.nextInt();
            case "ile_rośnie_jedzenie" -> ile_rośnie_jedzenie = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "koszt_tury" -> koszt_tury = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "pr_powielenia" -> pr_powielenia = sprawdź_zakres_wartości_double(wartość.nextDouble());
            case "ułamek_energii_rodzica" -> ułamek_energii_rodzica = sprawdź_zakres_wartości_double(wartość.nextDouble());
            case "limit_powielania" -> limit_powielania = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "co_ile_wypisz" -> co_ile_wypisz = sprawdź_zakres_wartości_int(param, wartość.nextInt());
            case "spis_instr" -> spis_instr = wartość.next().toCharArray();
            case "pr_usunięcia_instr" -> pr_usunięcia_instr = sprawdź_zakres_wartości_double(wartość.nextDouble());
            case "pr_dodania_instr" -> pr_dodania_instr = sprawdź_zakres_wartości_double(wartość.nextDouble());
            case "pr_zmiany_instr" -> pr_zmiany_instr = sprawdź_zakres_wartości_double(wartość.nextDouble());
        }
    }

    private void wczytaj_parametry(String plik) {
        try {
            Scanner scanner = new Scanner(new File(plik));

            for (int i = 0; i < ile_parametrów; i++) {
                String wiersz = scanner.nextLine();
                Scanner słowa = new Scanner(wiersz).useDelimiter(" ");
                String nazwa_parametru = słowa.next();

                for (String p : parametry) {
                    if (nazwa_parametru.equals(p)) {
                        wczytaj_wartość(p, słowa);
                        break;
                    }
                }
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void wczytaj_dane(String[] args) {
        sprawdź_parametry(args[1]);
        wczytaj_parametry(args[1]);
        System.out.println("PARAMETRY OK");

        sprawdź_planszę(args[0]);
        plansza = new Plansza(rozmiar_planszy_x, rozmiar_planszy_y, ile_tur);
        wczytaj_planszę(args[0]);
        System.out.println("PLANSZA OK\n");
    }
}
