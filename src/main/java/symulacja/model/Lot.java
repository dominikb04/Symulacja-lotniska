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
    private String start;
    private String cel;

    private Pogoda pogoda;
    private boolean odwolany;

    public void sprawdzPogode(Klimat klimat) {
        this.pogoda = klimat.generujPogode();
        this.odwolany = pogoda.czyAwaria();
    }

    public boolean isOdwolany() {
        return odwolany;
    }

    public Pogoda getPogoda() {
        return pogoda;
    }

    public Lot(Samolot samolot, String start, String cel) {
        this.samolot = samolot;
        this.start = start;
        this.cel = cel;
    }

    @Override
    public boolean zarezerwujMiejsce(Osoba osoba) {
        if(odwolany) return false;
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

    public String getStart() {
        return start;
    }
}

