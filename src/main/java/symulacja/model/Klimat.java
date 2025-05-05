package symulacja.model;

/**
 * Abstrakcyjna klasa reprezentująca klimat regionu.
 * Determinuje prawdopodobieństwo różnych typów pogód.
 */
public abstract class Klimat {
    protected final String nazwa;

    public Klimat(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Generuje aktualną pogodę dla tego klimatu
     */
    public abstract Pogoda generujPogode();

    public String getNazwa() {
        return nazwa;
    }
}