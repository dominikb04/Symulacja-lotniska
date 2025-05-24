package symulacja;

import symulacja.model.*;
import symulacja.ui.MapaPanel;
import symulacja.utils.ZapisWynikow;
import java.util.*;

public class Main {
    // Mapa lotnisk: nazwa miasta -> obiekt Lotnisko
    private static Map<String, Lotnisko> lotniska;

    // Lista wszystkich wygenerowanych lotów
    public static List<Lot> wszystkieLoty = new ArrayList<>();

    // Nazwy dni tygodnia, wykorzystywane przy podziale lotów
    private static final String[] DNI_TYGODNIA = {
            "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
    };

    // Punkt startowy programu - wywołuje GUI
    public static void main(String[] args) {
        symulacja.ui.OknoGlowne.wyswietlOkno();
    }

    // Zwraca listę lotów generowaną tylko raz (leniwa inicjalizacja)
    public static List<Lot> wygenerujLoty() {
        if (wszystkieLoty.isEmpty()) {
            wszystkieLoty = nowaSymulacja();
        }
        return wszystkieLoty;
    }

    /**
     * Główna metoda generująca loty na tydzień.
     * Wywoływana przez GUI po ustawieniu liczby lotów tygodniowo.
     */
    public static void generujLotyNaTydzien(int liczbaLotowWTygodniu) {
        wszystkieLoty.clear();

        Klimat europa = new KlimatUmiarkowany();
        List<String> miasta = new ArrayList<>(MapaPanel.getStolice().keySet());

        lotniska = new HashMap<>();
        for (String miasto : miasta) {
            lotniska.put(miasto, new Lotnisko(miasto, europa));
        }

        // Rozdziel losowo liczbę lotów na 7 dni tygodnia
        Map<String, Integer> lotyNaDzien = rozdzielLotyNaDni(liczbaLotowWTygodniu);

        for (String dzien : DNI_TYGODNIA) {
            int ileLotow = lotyNaDzien.get(dzien);
            for (int i = 0; i < ileLotow; i++) {
                Lot lot = generujLosowyLot(dzien);
                wszystkieLoty.add(lot);
            }
        }

        testSystemuPogodowego();
    }

    // Losowo przydziela liczbę lotów do dni tygodnia, z limitem max/2 na dzień
    private static Map<String, Integer> rozdzielLotyNaDni(int liczbaLotow) {
        Map<String, Integer> wynik = new LinkedHashMap<>();
        Random rand = new Random();
        int[] przydzial = new int[7];
        Arrays.fill(przydzial, 1); // minimalnie po 1 lot na dzień
        int suma = 7;

        while (suma < liczbaLotow) {
            int idx = rand.nextInt(7);
            if (przydzial[idx] < (liczbaLotow / 2)) {
                przydzial[idx]++;
                suma++;
            }
        }

        for (int i = 0; i < 7; i++) {
            wynik.put(DNI_TYGODNIA[i], przydzial[i]);
        }

        return wynik;
    }

    /**
     * Generuje pojedynczy losowy lot (miasto startowe, docelowe, typ samolotu, zaloga, pogoda, pasażerowie).
     * W przypadku pasażerskich samolotów dodaje pasażerów bezpośrednio (nie używa klasy Rezerwacja).
     */
    private static Lot generujLosowyLot(String dzien) {
        Random rand = new Random();
        List<Lotnisko> listaLotnisk = new ArrayList<>(lotniska.values());

        Lotnisko start = listaLotnisk.get(rand.nextInt(listaLotnisk.size()));
        String skad = start.getNazwa();

        String dokad;
        do {
            dokad = listaLotnisk.get(rand.nextInt(listaLotnisk.size())).getNazwa();
        } while (dokad.equals(skad));

        boolean towarowy = rand.nextDouble() < 0.2;
        Samolot samolot = towarowy
                ? new SamolotTowarowy("CT-" + UUID.randomUUID())
                : new SamolotPasazerski("SP-" + UUID.randomUUID(), 90 + rand.nextInt(71));

        Lot lot = new Lot(samolot, skad, dokad);
        lot.setDzienTygodnia(dzien);
        lot.setLotniskoStartowe(start);
        lot.wygenerujZaloge(towarowy ? 3 : 7);
        lot.sprawdzPogode(new KlimatUmiarkowany());

        // Generowanie pasażerów tylko dla nieodwołanych lotów pasażerskich
        if (!towarowy && !lot.isOdwolany()) {
            int maxMiejsc = samolot.getLiczbaMiejsc();
            boolean losowyNadmiar = rand.nextDouble() < 0.3;
            int liczbaProbowanych = losowyNadmiar
                    ? maxMiejsc + rand.nextInt(6)
                    : maxMiejsc - rand.nextInt(4);
            for (int j = 0; j < liczbaProbowanych; j++) {
                String paszport = Osoba.generujNumerPaszportu("PAS");
                Pasazer p = new Pasazer(paszport, skad, dokad);
                p.setDzienLotu(dzien);
                lot.zarezerwujMiejsce(p); // obecnie to metoda dodająca do listy pasazerow lub niewpuszczonych
            }
        }

        lot.zakonczLot(start);
        start.dodajLot(dzien, lot);
        return lot;
    }

    // Domyślny fallback - nieużywane, pozostawione dla zgodności z GUI
    private static List<Lot> nowaSymulacja() {
        return wszystkieLoty;
    }

    /**
     * Metoda testowa - wypisuje dane pogodowe i statystyki lotów na konsolę oraz do pliku.
     */
    private static void testSystemuPogodowego() {
        List<Lot> loty = wygenerujLoty();

        int liczbaTowarowych = 0;
        int liczbaWszystkich = 0;
        int liczbaOdwolanych = 0;
        Map<String, Lotnisko> unikalneLotniska = new HashMap<>();

        for (String dzien : DNI_TYGODNIA) {
            System.out.println("\n=== " + dzien.toUpperCase() + " ===");

            for (Lotnisko lotnisko : lotniska.values()) {
                List<Lot> lotyZDanegoDnia = lotnisko.getLotyNaDzien(dzien);

                for (Lot lot : lotyZDanegoDnia) {
                    liczbaWszystkich++;
                    System.out.printf("\nLot %d: %s -> %s (%s)\n", liczbaWszystkich,
                            lot.getStart(), lot.getCel(), lot.getSamolot().getNumerRejestracyjny());

                    System.out.println("Pogoda: " + (lot.getPogoda() != null ? lot.getPogoda().getOpis() : "Brak"));
                    System.out.printf("Szansa na awarie: %.1f%%\n",
                            lot.getPogoda() != null ? lot.getPogoda().getPrawdopodobienstwoAwarii() * 100 : 0);

                    if (lot.isOdwolany()) {
                        liczbaOdwolanych++;
                        double ryzyko = lot.getPogoda() != null ? lot.getPogoda().getPrawdopodobienstwoAwarii() : 0;
                        System.out.println(">>> LOT ODWOLANY! <<< Powód: " + lot.getPogoda().getOpis());
                    } else {
                        System.out.println("Lot odbywa się zgodnie z planem");

                        if (!(lot.getSamolot() instanceof SamolotTowarowy)) {
                            System.out.printf("Pasażerowie: %d/%d [LOT: %d]\n",
                                    lot.getRezerwacje().size(),
                                    lot.getSamolot().getLiczbaMiejsc(),
                                    lot.hashCode());
                        }

                        String skladZalogi = lot.getZaloga().stream()
                                .map(cz -> cz.getFunkcja().startsWith("p") ? "p" : "s")
                                .reduce("", String::concat);

                        System.out.printf("Załoga: %d/%d (%s)\n",
                                lot.getZaloga().size(),
                                (lot.getSamolot() instanceof SamolotPasazerski)
                                        ? ((SamolotPasazerski) lot.getSamolot()).getMaksymalnaZaloga()
                                        : 3,
                                skladZalogi);
                    }

                    if (lot.getSamolot() instanceof SamolotTowarowy) {
                        liczbaTowarowych++;
                        System.out.println("Typ samolotu: [TOWAROWY]");
                    }

                    unikalneLotniska.putIfAbsent(lot.getStart(), new Lotnisko(lot.getStart(), new KlimatUmiarkowany()));
                    unikalneLotniska.get(lot.getStart()).dodajPrzychod(
                            lotniska.get(lot.getStart()).getBudzet()
                    );
                }
            }
        }

        System.out.printf("\n=== Podsumowanie ===\nOdwołane loty: %d/%d (%.0f%%)\nUdane loty: %d/%d (%.0f%%)\n",
                liczbaOdwolanych, liczbaWszystkich, (liczbaOdwolanych / (double) liczbaWszystkich) * 100,
                liczbaWszystkich - liczbaOdwolanych, liczbaWszystkich,
                ((liczbaWszystkich - liczbaOdwolanych) / (double) liczbaWszystkich) * 100);

        System.out.printf("Loty towarowe: %d (%.0f%%)\n",
                liczbaTowarowych, (liczbaTowarowych / (double) liczbaWszystkich) * 100);

        System.out.println("\n=== Budżet lotnisk ===");
        for (Map.Entry<String, Lotnisko> entry : unikalneLotniska.entrySet()) {
            System.out.printf("Lotnisko %-10s | Budżet: %10.2f zł\n", entry.getKey(), entry.getValue().getBudzet());
        }
        ZapisWynikow.zapiszWyniki(wszystkieLoty, lotniska.values(), "symulacja_wyniki.txt");
        System.out.println("\nWyniki zostały zapisane do pliku: symulacja_wyniki.txt");
    }

    public static Map<String, Lotnisko> getLotniska() {
        return lotniska;
    }

    private static Lotnisko losoweLotnisko(Collection<Lotnisko> lotniska, Random rand) {
        List<Lotnisko> lista = new ArrayList<>(lotniska);
        return lista.get(rand.nextInt(lista.size()));
    }
}
