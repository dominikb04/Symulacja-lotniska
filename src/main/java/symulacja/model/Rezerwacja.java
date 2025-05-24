package symulacja.model;

/**
 * Reprezentuje rezerwację pasażera na konkretny lot.
 */
public class Rezerwacja {
    private Pasazer pasazer;
    private Lot lot;

    public Rezerwacja(Pasazer pasazer, Lot lot) {
        this.pasazer = pasazer;
        this.lot = lot;
    }
}
