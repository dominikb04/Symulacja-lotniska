package symulacja.model;

/**
 * Reprezentuje członka załogi samolotu.
 */
public class CzlonekZalogi extends Osoba {
    private final String funkcja; // "pilot" lub "stewardesa"

    public CzlonekZalogi(String numerPaszportu, String funkcja) {
        super(numerPaszportu);
        this.funkcja = funkcja;
    }

    public String getFunkcja() {
        return funkcja;
    }

    public double getPensja() {
        return funkcja.equalsIgnoreCase("pilot") ? 9000 : 6000;
    }

    @Override
    public String toString() {
        return funkcja.substring(0, 1).toUpperCase() + funkcja.substring(1).toLowerCase()
                + " (" + getNumerPaszportu() + ")";
    }
}
