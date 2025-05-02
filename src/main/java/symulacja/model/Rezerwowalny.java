package symulacja.model;

/**
 * Interfejs dla obiektów, które pozwalają na rezerwację.
 */
public interface Rezerwowalny {
    /**
     * Próbuje zarezerwować miejsce dla osoby.
     * @param osoba osoba dokonująca rezerwacji
     * @return true jeśli rezerwacja zakończona sukcesem
     */
    boolean zarezerwujMiejsce(Osoba osoba);
}
