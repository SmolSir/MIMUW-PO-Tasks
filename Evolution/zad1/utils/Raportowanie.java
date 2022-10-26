package zad1.utils;

import zad1.Symulacja;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Raportowanie extends Symulacja {

    private int min_długość_prog;
    private double śr_długość_prog;
    private int max_długość_prog;
    private int min_energia_roba;
    private double śr_energia_roba;
    private int max_energia_roba;
    private int min_wiek_roba;
    private double śr_wiek_roba;
    private int max_wiek_roba;

    public Raportowanie(){
    }

    private void reset_statystyki(int val) {
        int max_int = Integer.MAX_VALUE * val;
        int min_int = Integer.MIN_VALUE * val;
        min_długość_prog = max_int;
        śr_długość_prog = 0;
        max_długość_prog = min_int;
        min_energia_roba = max_int;
        śr_energia_roba = 0;
        max_energia_roba = min_int;
        min_wiek_roba = max_int;
        śr_wiek_roba = 0;
        max_wiek_roba = min_int;
    }

    private void rozpatrz_roba(Rob rob, int tura) {
        int długość_prog = rob.program.size();
        min_długość_prog = Math.min(długość_prog, min_długość_prog);
        śr_długość_prog += długość_prog;
        max_długość_prog = Math.max(długość_prog, max_długość_prog);

        int energia_roba = rob.energia;
        min_energia_roba = Math.min(energia_roba, min_energia_roba);
        śr_energia_roba += energia_roba;
        max_energia_roba = Math.max(energia_roba, max_energia_roba);

        int wiek_roba = tura - rob.pocz_tura;
        min_wiek_roba = Math.min(wiek_roba, min_wiek_roba);
        śr_wiek_roba += wiek_roba;
        max_wiek_roba = Math.max(wiek_roba, max_wiek_roba);
    }

    private void aktualizuj_statystykę(int tura) {
        if (plansza.roby.isEmpty()) {
            reset_statystyki(0);    // jeśli nie ma robów to wszystkie statystyki są zerowe
        }
        else {
            reset_statystyki(1);
        }

        for (Rob rob : plansza.roby) {
            rozpatrz_roba(rob, tura);
        }
        int ile_robów = (plansza.roby.isEmpty() ? 1 : plansza.roby.size());     // asekuracja dzielenia przez 0
        śr_długość_prog /= ile_robów;
        śr_energia_roba /= ile_robów;
        śr_wiek_roba /= ile_robów;
    }

    public void wypisz_statystykę(int tura) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.###", dfs);
        df.setMinimumFractionDigits(1);

        aktualizuj_statystykę(tura);

        String nr_tury = "tura:  " + tura;
        String raport_ile_robów = "rob:   " + plansza.roby.size();
        String raport_żywność = "żyw:   " + plansza.liczba_pól_z_jedzeniem;
        String raport_program = "prog:  " + min_długość_prog + " / " + df.format(śr_długość_prog) + " / " + max_długość_prog;
        String raport_energia = "energ: " + min_energia_roba + " / " + df.format(śr_energia_roba) + " / " + max_energia_roba;
        String raport_wiek = "wiek:  " + min_wiek_roba + " / " + df.format(śr_wiek_roba) + " / " + max_wiek_roba;

        System.out.println("STATYSTYKA");
        System.out.println(nr_tury);
        System.out.println(raport_ile_robów);
        System.out.println(raport_żywność);
        System.out.println(raport_program);
        System.out.println(raport_energia);
        System.out.println(raport_wiek);
        System.out.println();
    }

    // funkcja zamieniająca ArrayList znaków na String.
    private String get_string(ArrayList<Character> arrayList) {
        StringBuilder builder = new StringBuilder(arrayList.size());
        for (Character c: arrayList) {
            builder.append(c);
        }
        return builder.toString();
    }

    public void wypisz_roby(int tura) {
        int licznik = 0;
        for (Rob rob : plansza.roby) {
            String kierunek = switch (rob.kierunek) {
                case 0 -> "góra";
                case 1 -> "prawo";
                case 2 -> "dół";
                default -> "lewo";
            };
            licznik++;

            int wiek = tura - rob.pocz_tura;
            String program = (rob.program.isEmpty() ? "PUSTY PROGRAM" : get_string(rob.program));

            System.out.println("ROB " + licznik + ":");
            System.out.println("x_poz: " + rob.pozycja_x);
            System.out.println("y_poz: " + rob.pozycja_y);
            System.out.println("kierunek: " + kierunek);
            System.out.println("energia: " + rob.energia);
            System.out.println("wiek: " + wiek);
            System.out.println("program: " + program + "\n");

            //rob.rysuj_planszę(tura);    // Funkcja rysuje aktualny stan planszy z reprezentacją roba i jego kierunku.
        }                               // Zalecane wyłączenie dla plansz o większych wymiarach lub dużych ilości robów.

        if (licznik == 0) {
            System.out.println("ROBY WYGINĘŁY\n");
            System.exit(0);     // Jeżeli roby nie zdołały przeżyć, symulacja kończy się - można wyłączyć tę opcję.
        }
    }
}
