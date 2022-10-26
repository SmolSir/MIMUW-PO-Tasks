package zad1.utils;

import zad1.Symulacja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Plansza extends Symulacja {

    public int liczba_pól_z_jedzeniem;
    public int[] rosnące_jedzenie;
    public Pole[][] pole;
    public ArrayList<Rob> roby;

    public Plansza(){
    }

    public Plansza(int x, int y, int tury) {
        liczba_pól_z_jedzeniem = 0;
        rosnące_jedzenie = new int[tury + 1];
        pole = new Pole[x][y];
        roby = new ArrayList<>();
    }

    public void rozmieść_roby() {
        for (int i = 0; i < pocz_ile_robów; i++) {
            Random r = new Random();
            int x = r.nextInt(rozmiar_planszy_x);
            int y = r.nextInt(rozmiar_planszy_y);
            Rob rob = new Rob(pocz_program, pocz_energia, x, y, r.nextInt(4), 0);
            roby.add(rob);
        }
    }

    public void aktualizuj_jedzenie(int tura) {
        liczba_pól_z_jedzeniem += rosnące_jedzenie[tura];
    }

    public void usuń_martwego_roba(Rob rob) {
        roby.remove(rob);
    }

    /**
     * zad1.Symulacja ruchów odbywa się w sposób sprzyjający optymalnej mutacji programu, a jeśli mutacje
     * nie występują oddaje najbardziej realistyczny przebieg ruchów robów. Programy wszystkich robów
     * są wykonywane równocześnie, tzn. najpierw wykonujemy w losowej kolejności pierwszą instrukcję
     * w programach wszystkich robów, następnie ponownie w losowej kolejności wykonujemy drugą instrukcję
     * itd. dopóki conajmniej jeden rob ma k-tą instrukcję do wykonania. Oczywiście k-tą instrukcję
     * wykonują jedynie roby, które mają na tyle długi program oraz są żywe - zmarłe roby są usuwane
     * z symulacji.
     */
    public void symuluj_roby(int tura) {
        int id_instrukcji = 0;

        while (true){   // pętla while z warunkiem przerywającym działanie wewnątrz
            ArrayList<Rob> roby_symulowane = new ArrayList<>();
            for (Rob rob : roby) {
                if (rob.program.size() < id_instrukcji) {       // odjęto już koszt tury
                    continue;
                }
                if (rob.program.size() == id_instrukcji) {      // w poprzedniej iteracji ukończono wykonywanie programu
                    rob.energia -= koszt_tury;                  // więc należy pobrać koszt_tury
                    if (rob.energia < 0) {
                        usuń_martwego_roba(rob);
                    }
                }
                else {
                    roby_symulowane.add(rob);
                }
            }
            if (roby_symulowane.isEmpty()) {                    // warunek przerywający działanie pętli
                break;
            }

            Collections.shuffle(roby_symulowane);
            for (Rob rob : roby_symulowane) {
                rob.symuluj_instrukcję(tura, id_instrukcji);
            }
            id_instrukcji++;
        }
    }

    public void powiel_roby(int tura) {
        PowielanieMutacja pow_mut = new PowielanieMutacja();
        ArrayList<Rob> nowe_roby = new ArrayList<>();

        for (Rob rob : roby) {
            double random = ThreadLocalRandom.current().nextDouble(0,1);
            if (random < pr_powielenia && rob.energia >= limit_powielania) {
                Rob nowy_rob = pow_mut.powiel(rob, tura);
                nowe_roby.add(nowy_rob);
            }
        }

        roby.addAll(nowe_roby);
    }
}
