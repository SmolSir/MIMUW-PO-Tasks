package zad1.utils;

public class Pole extends Plansza{

    private int tura_odrośnięcia;

    public Pole(char rodz) {
        boolean żywieniowe = (rodz == 'x');
        if (rodz == 'x') {
            plansza.liczba_pól_z_jedzeniem++;
        }
        tura_odrośnięcia = (żywieniowe ? 0 : Integer.MAX_VALUE); // tworząc pole z jedzeniem jest ono "dojrzałe", a na polu bez jedzenia nie dojrzeje nigdy.
    }

    public boolean czy_dojrzałe(int tura) {
        return tura_odrośnięcia <= tura;
    }

    public void zjedz_pożywienie(Rob rob, int tura) {
        rob.energia += ile_daje_jedzenie;
        plansza.liczba_pól_z_jedzeniem--;

        this.tura_odrośnięcia = tura + ile_rośnie_jedzenie;
        if (this.tura_odrośnięcia <= ile_tur) {
            plansza.rosnące_jedzenie[this.tura_odrośnięcia]++;
        }
    }
}
