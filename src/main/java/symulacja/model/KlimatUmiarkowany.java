package symulacja.model;

/**
 * Klimat umiarkowany (np. Europa Środkowa).
 * Generuje pogodę z umiarkowaną, ale zauważalną szansą na awarie.
 */
public class KlimatUmiarkowany extends Klimat {

    public KlimatUmiarkowany() {
        super("Umiarkowany");
    }

    /**
     * Dla ulatwienia symulacji nie generujemy klimatu srodziemnomorskiego, poniewaz jest zblizony, a wymagalby o wiele wiecej kodu.
     * Generuje losowy stan pogody i odpowiadającą szansę na awarię.
     */
    @Override
    public Pogoda generujPogode() {
        double rand = Math.random();

        if (rand < 0.5) {
            return new Pogoda("Slonecznie", 0.01);      // 50% - bardzo niska szansa awarii
        } else if (rand < 0.75) {
            return new Pogoda("Mgliscie", 0.05);         // 25% - umiarkowane ryzyko
        } else if (rand < 0.92) {
            return new Pogoda("Deszcz", 0.10);           // 17% - podwyższone ryzyko
        } else {
            return new Pogoda("Burza", 0.25);            // 8% - wysokie ryzyko
        }
    }
}
