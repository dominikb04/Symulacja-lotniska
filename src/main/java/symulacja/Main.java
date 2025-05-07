package symulacja;

import symulacja.model.*;

import java.util.*;

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
        List<String> miasta = List.of(
                "Warszawa", "Berlin", "Paryz", "Madryt", "Rzym", "Lizbona",
                "Wieden", "Praga", "Bruksela", "Amsterdam", "Oslo", "Sztokholm",
                "Helsinki", "Kopenhaga", "Ateny", "Budapeszt"
        );

        List<Lot> loty = new ArrayList<>();
        Set<String> polaczenia = new HashSet<>();

        int liczbaLotow = 30;
        String dzien = "Poniedzialek";

        while (loty.size() < liczbaLotow) {
            String skad = miasta.get(new Random().nextInt(miasta.size()));
            String dokad;
            do {
                dokad = miasta.get(new Random().nextInt(miasta.size()));
            } while (dokad.equals(skad));

            String klucz = skad + "->" + dokad;
            if (polaczenia.contains(klucz)) continue;
            polaczenia.add(klucz);

            int pojemnosc = 90 + new Random().nextInt(71); // 90–160
            Samolot samolot = new SamolotPasazerski("SP-" + (100 + loty.size()), pojemnosc);
            Lot lot = new Lot(samolot, skad, dokad);
            lot.sprawdzPogode(europa);

            System.out.printf("\nLot %d: %s -> %s%n", loty.size() + 1, skad, dokad);
            System.out.println("Pogoda: " + lot.getPogoda().getOpis());
            System.out.println("Szansa na awarie: " + (lot.getPogoda().getPrawdopodobienstwoAwarii() * 100) + "%");

            if (lot.isOdwolany()) {
                System.out.println(">>> LOT ODWOLANY! <<< Powod: " + lot.getPogoda().getOpis());
            } else {
                System.out.println("Lot odbywa sie zgodnie z planem");

                int ilePasazerow = 70 + new Random().nextInt(60);
                for (int i = 0; i < ilePasazerow; i++) {
                    String paszport = "P" + (100000 + new Random().nextInt(900000));
                    Pasazer p = new Pasazer(paszport, skad, dokad, dzien);
                    boolean sukces = lot.zarezerwujMiejsce(p);
                    if (!sukces) {
                        System.out.println("Nie udalo sie dodac pasazera " + p.getNumerPaszportu());
                    }
                }

                int ileZalogi = 5 + new Random().nextInt(2);
                for (int i = 0; i < ileZalogi; i++) {
                    String paszport = "C" + (100000 + new Random().nextInt(900000));
                    CzlonekZalogi czlonek = new CzlonekZalogi(paszport);
                    lot.dodajCzlonkaZalogi(czlonek);
                }

                System.out.printf("Pasazerowie: %d/%d [LOT: %d]\n",
                        lot.getRezerwacje().size(),
                        samolot.getLiczbaMiejsc(),
                        lot.hashCode());

                System.out.printf("Zaloga: %d/%d\n",
                        lot.getZaloga().size(),
                        ((SamolotPasazerski) samolot).getMaksymalnaZaloga());
            }

            loty.add(lot);
        }

        // Statystyki
        long odwolane = loty.stream().filter(Lot::isOdwolany).count();
        System.out.printf("\n=== Podsumowanie ===\nOdwolane loty: %d/%d (%.0f%%)\nUdane loty: %d/%d\n",
                odwolane, liczbaLotow, (odwolane / (double) liczbaLotow) * 100, liczbaLotow - odwolane, liczbaLotow);
    }
}