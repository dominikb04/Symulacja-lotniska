package symulacja.model;

import java.util.ArrayList;
import java.util.List;

public class Pasazer extends Osoba {
    private List<Rezerwacja> rezerwacje = new ArrayList<>();
    private String skad;
    private String dokad;
    private String dzienLotu;

    public Pasazer(String numerPaszportu, String skad, String dokad) {
        super(numerPaszportu);
        this.skad = skad;
        this.dokad = dokad;
    }

    public void dodajRezerwacje(Rezerwacja r) {
        rezerwacje.add(r);
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public String getSkad() {
        return skad;
    }

    public String getDokad() {
        return dokad;
    }

    public String getDzienLotu() {
        return dzienLotu;
    }

    public void setDzienLotu(String dzienLotu) {
        this.dzienLotu = dzienLotu;
    }
    @Override
    public String toString() {
        return "Pasazer (" + getNumerPaszportu() + ")";
    }
}
