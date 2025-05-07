package symulacja.model;

/**
 * Klimat umiarkowany (np. Europa Środkowa)
 */
public class KlimatUmiarkowany extends Klimat {
    public KlimatUmiarkowany() {
        super("Umiarkowany");
    }

    // Dla ulatwienia symulacji nie generujemy klimatu srodziemnomorskiego, poniewaz jest zblizony, a wymagalby o wiele wiecej kodu.

    @Override
    public Pogoda generujPogode() {
        double rand = Math.random();
        if(rand < 0.6)      return new Pogoda("Slonecznie", 0);    // 60%
        else if(rand < 0.85) return new Pogoda("Mgliscie", 0.015);   // 25%
        else if(rand < 0.95) return new Pogoda("Deszcz", 0.02);       // 10%
        else                return new Pogoda("Burza", 0.05);         // 5%
    }
}