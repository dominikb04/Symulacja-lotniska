package symulacja.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje lotnisko z listą dostępnych lotów.
 */
public class Lotnisko {
    private String nazwa;
    private List<Lot> dostepneLoty = new ArrayList<>();

    public Lotnisko(String nazwa) {
        this.nazwa = nazwa;
    }

    public void dodajLot(Lot lot) {
        dostepneLoty.add(lot);
    }

    public List<Lot> getDostepneLoty() {
        return dostepneLoty;
    }

    public String getNazwa() {
        return nazwa;
    }
}

