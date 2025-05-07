package symulacja.model;

/**
 * Klasa bazowa dla wszystkich osób w systemie (pasażerowie, załoga).
 */
public abstract class Osoba {
    private String numerPaszportu;

    public Osoba(String numerPaszportu) {
        this.numerPaszportu = numerPaszportu;
    }

    public String getNumerPaszportu() {
        return numerPaszportu;
    }

    @Override
    public String toString() {
        return "Paszport: " + numerPaszportu;
    }
}