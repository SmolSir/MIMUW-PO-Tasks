package zad1;

import zad1.utils.Plansza;
import zad1.utils.Raportowanie;
import zad1.utils.Wczytywanie;

import java.util.ArrayList;

public class Symulacja {

    /**
     * PRZEBIEG SYMULACJI
     * - na początku tury "dojrzewa" jedzenie i aktualizowana jest liczba pól z "dojrzałym" jedzeniem,
     *      przy czym jedzenie dojrzewa pośrednio - odnotowujemy ten fakt poprawnie w ilości dojrzałego
     *      jedzenia, ale nie interesuje nas które jedzenie dojrzało, nie zmieniamy żadnych atrybutów Pól.
     * - roby zgodnie z kolejnością tworzenia wykonują swoje programy, przy czym
     *      1) jeśli rob trafi na pole z "dojrzałym" jedzeniem, to zjada je całe i zamienia na energię,
     *          a zad1.utils.Pole z którego zjadł "dojrzałe" jedzenie zostaje zaktualizowane informacją w której turze
     *          ponownie "dojrzeje",
     *      2) energia za wykonanie instrukcji pobierana jest od roba przed jej wykonaniem, a nie po wykonaniu.
     * - od roba pobrany zostaje koszt tury (przed powielaniem)
     * - jeżeli rob ma wystarczająco energii, to następuje próba powielenia, przy czym nowy rob ma
     *      floor(ułamek_energii_rodzica * energia_rodzica) energii i taką ilość zabieramy rodzicowi, ponieważ
     *      zgodnie z treścią energię mierzymy przy pomocy jednostek. Ponadto w ten sposób nie powstają energie
     *      ułamkowe, które zmniejszają czytelność statystyk i w niewielkim stopniu wpływają na symulację.
     * - na koniec tury odbywa się raportowanie w wersji krótkiej bądź krótkiej i długiej, w zależności od
     *      numeru bieżącej tury. Tury liczymy od 1 do ile_tur.
     */

    public static int rozmiar_planszy_x;
    public static int rozmiar_planszy_y;
    public static int ile_tur;
    public static int pocz_ile_robów;
    public static ArrayList<Character> pocz_program;
    public static int pocz_energia;
    public static int ile_daje_jedzenie;
    public static int ile_rośnie_jedzenie;
    public static int koszt_tury;
    public static double pr_powielenia;
    public static double ułamek_energii_rodzica;
    public static int limit_powielania;
    public static int co_ile_wypisz;
    public static char[] spis_instr;
    public static double pr_usunięcia_instr;
    public static double pr_dodania_instr;
    public static double pr_zmiany_instr;

    public static Plansza plansza;

    public static void main (String[] args) {

        Wczytywanie wczytywanie = new Wczytywanie();
        Raportowanie raportowanie = new Raportowanie();

        wczytywanie.wczytaj_dane(args);
        plansza.rozmieść_roby();
        raportowanie.wypisz_statystykę(0);
        raportowanie.wypisz_roby(0);

        System.out.println("SYMULACJA ROZPOCZĘTA\n");

        for (int tura = 1; tura <= ile_tur; tura++) {
            plansza.aktualizuj_jedzenie(tura);
            plansza.symuluj_roby(tura);
            plansza.powiel_roby(tura);

            raportowanie.wypisz_statystykę(tura);
            if (tura % co_ile_wypisz == 0 || tura == ile_tur) raportowanie.wypisz_roby(tura);
        }
    }
}
