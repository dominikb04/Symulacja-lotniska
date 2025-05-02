package symulacja.model;

/**
 * Klasa bazowa dla różnych typów samolotów.
 */
public abstract class Samolot {
    protected String numerRejestracyjny;
    protected int maksymalnaLiczbaPasazerow;

    public Samolot(String numerRejestracyjny, int maks) {
        this.numerRejestracyjny = numerRejestracyjny;
        this.maksymalnaLiczbaPasazerow = maks;
    }

    public abstract double obliczZuzyciePaliwa(int liczbaPasazerow);
}