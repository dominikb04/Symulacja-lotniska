package symulacja.model;

import java.util.*;

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
    private Lotnisko lotniskoStartowe;
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

    public void wygenerujZaloge(int liczbaCzlonkowZalogi) {
        zaloga = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String paszport = Osoba.generujNumerPaszportu("ZAL");
            zaloga.add(new CzlonekZalogi(paszport, "pilot"));
        }
        for (int i = 3; i < liczbaCzlonkowZalogi; i++) {
            String paszport = Osoba.generujNumerPaszportu("ZAL");
            zaloga.add(new CzlonekZalogi(paszport, "stewardesa"));
        }
        Collections.shuffle(zaloga);
    }
    public String opisZalogi() {
        return "Załoga: " + zaloga.size();
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
    public Lotnisko getLotniskoStartowe() {
        return lotniskoStartowe;
    }
    private String dzienTygodnia;

    public String getDzienTygodnia() {
        return dzienTygodnia;
    }

    public void setDzienTygodnia(String dzienTygodnia) {
        this.dzienTygodnia = dzienTygodnia;
    }
    public String getCel() {
        return cel;
    }
    public Samolot getSamolot() {
        return samolot;
    }
    public String getStart() {
        return start;
    }
    public void setLotniskoStartowe(Lotnisko lotnisko) {
        this.lotniskoStartowe = lotnisko;
    }
    public void zakonczLot(Lotnisko lotniskoStartowe) {
        if (!isOdwolany()) {
            lotniskoStartowe.dodajPrzychod(pasazerowie.size() * 1000);
            double kosztZalogi = 0;
            for (CzlonekZalogi cz : zaloga) {
                kosztZalogi += cz.getPensja();
            }
            lotniskoStartowe.dodajKoszt(kosztZalogi);
        } else {
            lotniskoStartowe.dodajStrate(pasazerowie.size() * 1500);
        }
    }
}

