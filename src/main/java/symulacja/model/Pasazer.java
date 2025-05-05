package symulacja.model;

import java.util.ArrayList;
import java.util.List;

public class Pasazer extends Osoba {
    private String numerPaszportu;
    private List<Rezerwacja> rezerwacje = new ArrayList<>();
    private String skad;
    private String dokad;
    private String dzienLotu;

    public Pasazer(String imie, String nazwisko, String numerPaszportu, String skad, String dokad, String dzienLotu) {
        super(imie, nazwisko);
        this.numerPaszportu = numerPaszportu;
        this.skad = skad;
        this.dokad = dokad;
        this.dzienLotu = dzienLotu;
    }

    public void dodajRezerwacje(Rezerwacja r) {
        rezerwacje.add(r);
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public String getNumerPaszportu() {
        return numerPaszportu;
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
}

