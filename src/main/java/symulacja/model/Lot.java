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
    private List<CzlonekZalogi> zaloga = new ArrayList<>();
    private List<Rezerwacja> rezerwacje = new ArrayList<>();
    private Pogoda pogoda;
    private boolean odwolany;

    public void dodajRezerwacje(Rezerwacja r) {
        rezerwacje.add(r);
    }
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
        if (odwolany) return false;
        if (!(osoba instanceof Pasazer)) return false;

        if (pasazerowie.size() < samolot.getLiczbaMiejsc()) {
            Pasazer pasazer = (Pasazer) osoba;
            pasazerowie.add(pasazer);

            // Dodaj rezerwację
            Rezerwacja r = new Rezerwacja(pasazer, this);
            pasazer.dodajRezerwacje(r);
            rezerwacje.add(r);

            return true;
        }
        return false;
    }

    public void dodajCzlonkaZalogi(CzlonekZalogi czlonek) {
        if (samolot instanceof SamolotPasazerski) {
            int maxZaloga = ((SamolotPasazerski) samolot).getMaksymalnaZaloga();
            if (zaloga.size() >= maxZaloga) {
                System.out.println("Nie można dodać więcej członków załogi – osiągnięto limit (" + maxZaloga + ")");
                return;
            }
        }
        zaloga.add(czlonek);
    }

    public List<CzlonekZalogi> getZaloga() {
        return zaloga;
    }
    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
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

