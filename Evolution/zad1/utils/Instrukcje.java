package zad1.utils;

import zad1.Symulacja;

public class Instrukcje extends Rob{

    /**
     * Tablice przechowujące wektory przesunięcia współrzędnych roba dla określonych pól otaczających go.
     * Indeksy 0 - 3 wyrażają wektory zgodne z oznaczeniami kierunków (0 - w górę, 1 - w prawo, 2 - w dół, 3 - w lewo),
     * Indeksy 4 - 7 wyrażają wektory będące sumą dwóch wektorów 0 - 3:
     * wektor_4 = wektor_0 + wektor_1  (w górę, w prawo),
     * wektor_5 = wektor_1 + wektor_2  (w prawo, w dół),
     * wektor_6 = wektor_2 + wektor_3  (w dół, w lewo),
     * wektor_7 = wektor_3 + wektor_0  (w lewo, w górę).
     */
    private final int[] wektor_x = new int[]{0, 1, 0, -1, 1, 1, -1, -1};
    private final int[] wektor_y = new int[]{1, 0, -1, 0, 1, -1, -1, 1};

    public Instrukcje(){
    }

    public void wykonaj_instrukcję(Rob rob, int tura, char instrukcja) {
        switch (instrukcja) {
            case 'l' -> lewo(rob);
            case 'p' -> prawo(rob);
            case 'i' -> idź(rob, tura);
            case 'w' -> wąchaj(rob, tura);
            case 'j' -> jedz(rob, tura);
        }
    }

    private void przejdz_na_pole(int x, int y, Rob rob, int tura) {
        rob.pozycja_x = x;
        rob.pozycja_y = y;
        if (Symulacja.plansza.pole[x][y].czy_dojrzałe(tura)) {
            Symulacja.plansza.pole[x][y].zjedz_pożywienie(rob, tura);
        }
    }

    private int przesuń_x(int x, int wek) {
        return (x + wek + Symulacja.rozmiar_planszy_x) % Symulacja.rozmiar_planszy_x;
    }

    private int przesuń_y(int y, int wek) {
        return (y + wek + Symulacja.rozmiar_planszy_y) % Symulacja.rozmiar_planszy_y;
    }

    private void lewo(Rob rob) {
        rob.kierunek = (rob.kierunek + 3) % 4;
    }

    private void prawo(Rob rob) {
        rob.kierunek = (rob.kierunek + 1) % 4;
    }

    private void idź(Rob rob, int tura) {
        int nowy_x = przesuń_x(rob.pozycja_x, wektor_x[rob.kierunek]);
        int nowy_y = przesuń_y(rob.pozycja_y, wektor_y[rob.kierunek]);

        przejdz_na_pole(nowy_x, nowy_y, rob, tura);
    }

    private void wąchaj(Rob rob, int tura) {
        for (int i = 0; i < 4; i++) {
            int sprawdz_x = przesuń_x(rob.pozycja_x, wektor_x[i]);
            int sprawdz_y = przesuń_y(rob.pozycja_y, wektor_y[i]);

            if (Symulacja.plansza.pole[sprawdz_x][sprawdz_y].czy_dojrzałe(tura)) {
                rob.kierunek = i;
                return;
            }
        }
    }

    private void jedz(Rob rob, int tura) {
        for (int i = 0; i < 8; i++) {
            int sprawdz_x = przesuń_x(rob.pozycja_x, wektor_x[i]);
            int sprawdz_y = przesuń_y(rob.pozycja_y, wektor_y[i]);

            if (Symulacja.plansza.pole[sprawdz_x][sprawdz_y].czy_dojrzałe(tura)) {
                przejdz_na_pole(sprawdz_x, sprawdz_y, rob, tura);
                return;
            }
        }
    }
}
