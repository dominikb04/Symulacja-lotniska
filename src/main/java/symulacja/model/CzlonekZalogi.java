package symulacja.model;

/**
 * Reprezentuje członka załogi samolotu.
 */
public class CzlonekZalogi extends Osoba {

    public CzlonekZalogi(String numerPaszportu) {
        super(numerPaszportu);
    }

    @Override
    public String toString() {
        return "Czlonek załogi (" + getNumerPaszportu() + ")";
    }
}