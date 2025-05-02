package symulacja.model;

import java.util.ArrayList;
import java.util.List;

public class Pasazer extends Osoba {
    private String numerPaszportu;
    private List<Rezerwacja> rezerwacje = new ArrayList<>();

    public Pasazer(String imie, String nazwisko, String numerPaszportu) {
        super(imie, nazwisko);
        this.numerPaszportu = numerPaszportu;
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
}

