package symulacja.model;

import java.util.*;

/**
 * Reprezentuje pojedynczy lot obsługiwany przez samolot.
 * Może być rezerwowany przez pasażerów i posiada załogę, pogodę, lotnisko startowe itd.
 */
public class Lot implements Rezerwowalny {

    // === Pola ===
    private Samolot samolot;
    private String start;
    private String cel;
    private String dzienTygodnia;

    private List<Pasazer> pasazerowie = new ArrayList<>();
    private List<CzlonekZalogi> zaloga = new ArrayList<>();
    private List<Rezerwacja> rezerwacje = new ArrayList<>();
    private List<Pasazer> niewpuszczeni = new ArrayList<>();

    private Pogoda pogoda;
    private boolean odwolany;
    private Lotnisko lotniskoStartowe;

    // === Konstruktor ===
    public Lot(Samolot samolot, String start, String cel) {
        this.samolot = samolot;
        this.start = start;
        this.cel = cel;
    }

    // === Metody publiczne ===

    public void setDzienTygodnia(String dzienTygodnia) {
        this.dzienTygodnia = dzienTygodnia;
    }

    public String getDzienTygodnia() {
        return dzienTygodnia;
    }

    public String getStart() {
        return start;
    }

    public String getCel() {
        return cel;
    }

    public Samolot getSamolot() {
        return samolot;
    }

    public List<Pasazer> getPasazerowie() {
        return pasazerowie;
    }

    public List<CzlonekZalogi> getZaloga() {
        return zaloga;
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public List<Pasazer> getNiewpuszczeni() {
        return niewpuszczeni;
    }

    public Pogoda getPogoda() {
        return pogoda;
    }

    public boolean isOdwolany() {
        return odwolany;
    }

    public void setLotniskoStartowe(Lotnisko lotnisko) {
        this.lotniskoStartowe = lotnisko;
    }

    /**
     * Generuje pogodę na podstawie klimatu i sprawdza czy lot zostaje odwołany.
     */
    public void sprawdzPogode(Klimat klimat) {
        this.pogoda = klimat.generujPogode();
        this.odwolany = pogoda.czyAwaria();
    }

    /**
     * Rezerwuje miejsce dla pasażera w samolocie (jeśli nie jest odwołany i są miejsca).
     */
    @Override
    public boolean zarezerwujMiejsce(Osoba osoba) {
        if (odwolany || !(osoba instanceof Pasazer pasazer)) return false;

        if (pasazerowie.size() < samolot.getLiczbaMiejsc()) {
            pasazerowie.add(pasazer);

            Rezerwacja r = new Rezerwacja(pasazer, this);
            pasazer.dodajRezerwacje(r);
            rezerwacje.add(r);

            return true;
        } else {
            niewpuszczeni.add(pasazer);
            return false;
        }
    }

    /**
     * Generuje losową załogę do lotu: 3 pilotów + reszta stewardesy.
     */
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

    /**
     * Zamyka lot i rozlicza przychody/koszty dla lotniska startowego.
     */
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
