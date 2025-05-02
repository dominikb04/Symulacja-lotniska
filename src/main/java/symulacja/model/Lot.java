package symulacja.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje lot obsługiwany przez określony samolot.
 * Lot może być rezerwowany przez pasażerów.
 */
public class Lot implements Rezerwowalny {
    private Samolot samolot;
    private List<Pasazer> pasazerowie = new ArrayList<>();
    private String cel;

    public Lot(Samolot samolot, String cel) {
        this.samolot = samolot;
        this.cel = cel;
    }

    @Override
    public boolean zarezerwujMiejsce(Osoba osoba) {
        if (!(osoba instanceof Pasazer)) return false;

        if (pasazerowie.size() < samolot.maksymalnaLiczbaPasazerow) {
            pasazerowie.add((Pasazer) osoba);
            return true;
        }
        return false;
    }

    public List<Pasazer> getPasazerowie() {
        return pasazerowie;
    }

    public String getCel() {
        return cel;
    }
}

