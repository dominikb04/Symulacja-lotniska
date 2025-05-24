package symulacja.model;

/**
 * Klasa abstrakcyjna reprezentująca ogólny samolot.
 * Używana jako baza dla samolotów pasażerskich i towarowych.
 */
public abstract class Samolot {

    // === Pola ===
    protected String numerRejestracyjny;
    protected int maksymalnaLiczbaPasazerow;

    // === Konstruktory ===

    /**
     * Konstruktor ogólny (głównie dla pasażerskich).
     */
    public Samolot(String numerRejestracyjny, int maksymalnaLiczbaPasazerow) {
        this.numerRejestracyjny = numerRejestracyjny;
        this.maksymalnaLiczbaPasazerow = maksymalnaLiczbaPasazerow;
    }

    /**
     * Konstruktor uproszczony (dla samolotów towarowych).
     */
    public Samolot(String numerRejestracyjny) {
        this(numerRejestracyjny, 0);
    }

    // === Gettery ===

    public String getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    public int getLiczbaMiejsc() {
        return maksymalnaLiczbaPasazerow;
    }

    // === Metody abstrakcyjne ===

    public abstract double obliczZuzyciePaliwa(int liczbaPasazerow);
}
