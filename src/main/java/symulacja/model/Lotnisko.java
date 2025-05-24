package symulacja.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje lotnisko, które obsługuje loty w ciągu tygodnia oraz posiada budżet.
 */
public class Lotnisko {

    // === Pola ===
    private String nazwa;
    private final Klimat klimat;
    private Map<String, List<Lot>> lotyWTygodniu = new HashMap<>();
    private double budzet = 0;

    // === Konstruktor ===
    public Lotnisko(String nazwa, Klimat klimat) {
        this.nazwa = nazwa;
        this.klimat = klimat;
    }

    // === Gettery ===

    public String getNazwa() {
        return nazwa;
    }

    public double getBudzet() {
        return budzet;
    }

    public Map<String, List<Lot>> getLotyWTygodniu() {
        return lotyWTygodniu;
    }

    /**
     * Zwraca listę lotów przypisanych do konkretnego dnia tygodnia.
     */
    public List<Lot> getLotyNaDzien(String dzien) {
        return lotyWTygodniu.getOrDefault(dzien, new ArrayList<>());
    }

    /**
     * (Tymczasowe) Zwraca wszystkie loty niezależnie od dnia tygodnia.
     */
    @Deprecated
    public List<Lot> getDostepneLoty() {
        List<Lot> wszystkie = new ArrayList<>();
        for (List<Lot> dzienneLoty : lotyWTygodniu.values()) {
            wszystkie.addAll(dzienneLoty);
        }
        return wszystkie;
    }

    // === Modyfikatory budżetu ===

    public void dodajPrzychod(double kwota) {
        budzet += kwota;
    }

    public void dodajKoszt(double kwota) {
        budzet -= kwota;
    }

    public void dodajStrate(double kwota) {
        budzet -= kwota;
    }

    // === Modyfikatory listy lotów ===

    /**
     * Dodaje lot do listy przypisanej do danego dnia tygodnia.
     */
    public void dodajLot(String dzien, Lot lot) {
        lotyWTygodniu.computeIfAbsent(dzien, k -> new ArrayList<>()).add(lot);
    }
}

