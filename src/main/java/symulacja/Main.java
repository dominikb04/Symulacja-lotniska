package symulacja;

import symulacja.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Punkt wejścia aplikacji – uruchamia GUI i testuje system pogodowy
 */
public class Main {
    public static void main(String[] args) {
        // 1. Uruchomienie GUI
        symulacja.ui.OknoGlowne.wyswietlOkno();

        // 2. Test systemu pogodowego
        testSystemuPogodowego();
    }

    private static void testSystemuPogodowego() {
        System.out.println("\n=== Rozpoczęcie testu systemu pogodowego ===");

        Klimat europa = new KlimatUmiarkowany();
        Samolot samolot = new SamolotPasazerski("SP-001", 150);
        List<Lot> testoweLoty = new ArrayList<>();

        // Generujemy 10 lotów testowych
        for (int i = 1; i <= 10; i++) {
            Lot lot = new Lot(samolot, "Warszawa", "Paryż");
            lot.sprawdzPogode(europa);
            testoweLoty.add(lot);

            System.out.printf("\nLot %d: %s -> %s%n", i, lot.getStart(), lot.getCel());
            System.out.println("Pogoda: " + lot.getPogoda().getOpis());
            System.out.println("Szansa na awarię: " + (lot.getPogoda().getPrawdopodobienstwoAwarii() * 100) + "%");

            if (lot.isOdwolany()) {
                System.out.println(">>> LOT ODWOŁANY! <<< Powód: " + lot.getPogoda().getOpis());
            } else {
                System.out.println("Lot odbywa się zgodnie z planem");

                // Symulacja rezerwacji
                Pasazer pasazer = new Pasazer("Jan", "Kowalski", "AB123", "Warszawa", "Paryż", "2023-06-01");
                if (lot.zarezerwujMiejsce(pasazer)) {
                    System.out.println("Rezerwacja przebiegła pomyślnie");
                } else {
                    System.out.println("Nie udało się zarezerwować miejsca");
                }
            }
        }

        // Statystyki
        long odwołaneLoty = testoweLoty.stream().filter(Lot::isOdwolany).count();
        System.out.printf("\n=== Podsumowanie ===\n" +
                        "Odwołane loty: %d/10 (%.0f%%)\n" +
                        "Udane loty: %d/10\n",
                odwołaneLoty, (odwołaneLoty/10.0)*100, 10-odwołaneLoty);
    }
}