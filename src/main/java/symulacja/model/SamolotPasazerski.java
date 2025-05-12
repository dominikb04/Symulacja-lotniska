package symulacja.model;

/**
 * Klasa reprezentująca samolot pasażerski.
 */

public class SamolotPasazerski extends Samolot {
    private final int maksymalnaZaloga = 7;

    public SamolotPasazerski(String numerRejestracyjny, int liczbaMiejsc) {
        super(numerRejestracyjny, liczbaMiejsc);
    }

    @Override
    public double obliczZuzyciePaliwa(int liczbaPasazerow) {
        return 5.0 + 0.1 * liczbaPasazerow;
    }

    public int getMaksymalnaZaloga() {
        return maksymalnaZaloga;
    }

}
