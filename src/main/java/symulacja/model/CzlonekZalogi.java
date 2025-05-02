package symulacja.model;

/**
 * Reprezentuje członka załogi samolotu.
 */
public class CzlonekZalogi extends Osoba {
    private String rola; // np. pilot, steward

    public CzlonekZalogi(String imie, String nazwisko, String rola) {
        super(imie, nazwisko);
        this.rola = rola;
    }

    public String getRola() {
        return rola;
    }
}
