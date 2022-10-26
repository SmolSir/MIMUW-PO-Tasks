package zad1.utils;

import java.util.ArrayList;

public class Rob extends Plansza{

    public ArrayList<Character> program;
    public int energia;
    public int pozycja_x;
    public int pozycja_y;
    public int kierunek;    // 0 - góra, 1 - prawo, 2 - dół, 3 - lewo
    public int pocz_tura;

    public Rob(){
    }

    public Rob(ArrayList<Character> prog, int energ, int x, int y, int kier, int tura) {
        program = prog;
        energia = energ;
        pozycja_x = x;
        pozycja_y = y;
        kierunek = kier;
        pocz_tura = tura;
    }

    public void symuluj_instrukcję(int tura, int id_instrukcji) {
        Instrukcje instrukcje = new Instrukcje();
        char instrukcja = this.program.get(id_instrukcji);

        this.energia--;
        if (this.energia < 0) {
            plansza.usuń_martwego_roba(this);
        }

        instrukcje.wykonaj_instrukcję(this, tura, instrukcja);
    }

    // Funkcja dla danego roba wypisuje aktualną planszę z uwzględnieniem pozycji i kierunku roba.
    // Można włączyć w klasie zad1.utils.Raportowanie, w funkcji wypisz_roby.
    public void rysuj_planszę(int tura) {
        for (int y = rozmiar_planszy_y - 1; y >= 0; y--) {
            char[] wiersz = new char[rozmiar_planszy_x];
            for (int x = 0; x < rozmiar_planszy_x; x++) {
                if (this.pozycja_x == x && this.pozycja_y == y) {
                    switch (kierunek) {
                        case 0 -> wiersz[x] = '^';
                        case 1 -> wiersz[x] = '>';
                        case 2 -> wiersz[x] = 'v';
                        case 3 -> wiersz[x] = '<';
                    }
                }
                else if (plansza.pole[x][y].czy_dojrzałe(tura)) {
                    wiersz[x] = 'x';
                }
                else wiersz[x] = ' ';
            }
            System.out.println(wiersz);
        }
        System.out.println();
    }

}
