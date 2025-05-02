package symulacja.model;

/**
 * Klasa reprezentująca samolot pasażerski.
 */
public class SamolotPasazerski extends Samolot {

    public SamolotPasazerski(String nr, int max) {
        super(nr, max);
    }

    @Override
    public double obliczZuzyciePaliwa(int liczbaPasazerow) {
        return 5.0 + 0.1 * liczbaPasazerow; // przykładowy model zużycia
    }
}
