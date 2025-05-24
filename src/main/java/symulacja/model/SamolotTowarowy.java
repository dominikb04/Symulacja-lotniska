package symulacja.model;

public class SamolotTowarowy extends Samolot {

    public SamolotTowarowy(String numerRejestracyjny) {
        super(numerRejestracyjny);
    }

    @Override
    public double obliczZuzyciePaliwa(int liczbaPasazerow) {
        return 5000.0; // stałe zużycie paliwa, niezależne od pasażerów
    }
}