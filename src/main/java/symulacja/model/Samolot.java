package symulacja.model;

/**
 * Klasa bazowa dla różnych typów samolotów.
 */
public abstract class Samolot {
    protected String numerRejestracyjny;
    protected int maksymalnaLiczbaPasazerow;

    public Samolot(String numerRejestracyjny, int maksymalnaLiczbaPasazerow) {
        this.numerRejestracyjny = numerRejestracyjny;
        this.maksymalnaLiczbaPasazerow = maksymalnaLiczbaPasazerow;
    }

    public abstract double obliczZuzyciePaliwa(int liczbaPasazerow);

    public int getLiczbaMiejsc() {
        return maksymalnaLiczbaPasazerow;
    }
}