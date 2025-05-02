package symulacja.model;

/**
 * Klasa bazowa dla wszystkich osób w systemie (pasażerowie, załoga).
 */
public abstract class Osoba {
    protected String imie;
    protected String nazwisko;

    public Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getPelneImie() {
        return imie + " " + nazwisko;
    }
}
