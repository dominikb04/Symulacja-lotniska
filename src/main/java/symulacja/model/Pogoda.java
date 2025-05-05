package symulacja.model;

/**
 * Reprezentuje aktualne warunki pogodowe i ich wpływ na loty
 */
public class Pogoda {
    private final String opis;
    private final double prawdopodobienstwoAwarii;

    public Pogoda(String opis, double prawdopodobienstwoAwarii) {
        this.opis = opis;
        this.prawdopodobienstwoAwarii = Math.min(0.05, prawdopodobienstwoAwarii); // Maksymalnie 5%
    }

    /**
     * Sprawdza czy w tych warunkach wystąpiła awaria
     */
    public boolean czyAwaria() {
        return Math.random() < prawdopodobienstwoAwarii;
    }

    public String getOpis() {
        return opis;
    }

    public double getPrawdopodobienstwoAwarii() {
        return prawdopodobienstwoAwarii;
    }
}