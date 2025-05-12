package symulacja.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reprezentuje lotnisko z listą dostępnych lotów.
 */
public class Lotnisko {
    private String nazwa;
    private final Klimat klimat;
    private Map<String, List<Lot>> lotyWTygodniu = new HashMap<>();

    private double budzet = 0;

    public void dodajPrzychod(double kwota) {
        budzet += kwota;
    }

    public void dodajKoszt(double kwota) {
        budzet -= kwota;
    }

    public void dodajStrate(double kwota) {
        budzet -= kwota;
    }

    public double getBudzet() {
        return budzet;
    }
    public Lotnisko(String nazwa, Klimat klimat) {
        this.nazwa = nazwa;
        this.klimat = klimat;
    }

    public List<Lot> getLotyNaDzien(String dzien) {
        return lotyWTygodniu.getOrDefault(dzien, new ArrayList<>());
    }

    public Map<String, List<Lot>> getLotyWTygodniu() {
        return lotyWTygodniu;
    }

    public void dodajLot(String dzien, Lot lot) {
        lotyWTygodniu.computeIfAbsent(dzien, k -> new ArrayList<>()).add(lot);
    }

    @Deprecated
    public List<Lot> getDostepneLoty() {
        // Zwraca wszystkie loty z całego tygodnia — tylko tymczasowo!
        List<Lot> wszystkie = new ArrayList<>();
        for (List<Lot> dzienneLoty : lotyWTygodniu.values()) {
            wszystkie.addAll(dzienneLoty);
        }
        return wszystkie;
    }

    public String getNazwa() {
        return nazwa;
    }
}

