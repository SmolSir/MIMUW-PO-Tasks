package zad1.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PowielanieMutacja extends Rob{

    public PowielanieMutacja() {
    }

    private ArrayList<Character> mutuj_program(ArrayList<Character> prog) {
        Random r = new Random();
        double random;

        random = ThreadLocalRandom.current().nextDouble(0,1);
        if (random < pr_usunięcia_instr && !prog.isEmpty()) {
            prog.remove(prog.size() - 1);
        }

        random = ThreadLocalRandom.current().nextDouble(0,1);
        if (random < pr_dodania_instr) {
            int id = r.nextInt(spis_instr.length);
            program.add(spis_instr[id]);
        }

        random = ThreadLocalRandom.current().nextDouble(0,1);
        if (random < pr_zmiany_instr && !prog.isEmpty()) {
            int id = r.nextInt(spis_instr.length);
            int id_zmiany = r.nextInt(program.size());
            program.set(id_zmiany, spis_instr[id]);
        }

        return prog;
    }

    private void przelicz_energie(Rob dziecko, Rob rodzic) {
        int delta_energ = (int) (rodzic.energia * ułamek_energii_rodzica);

        dziecko.energia = delta_energ;
        rodzic.energia -= delta_energ;
    }

    public Rob powiel(Rob rodzic, int tura) {
        Rob dziecko = new Rob();

        dziecko.program = mutuj_program(rodzic.program);
        przelicz_energie(dziecko, rodzic);
        dziecko.pozycja_x = rodzic.pozycja_x;
        dziecko.pozycja_y = rodzic.pozycja_y;
        dziecko.kierunek = (rodzic.kierunek + 2) % 4;   // obrót o 180 stopni
        dziecko.pocz_tura = tura;

        return dziecko;
    }
}
