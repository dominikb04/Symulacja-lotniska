package symulacja;

import symulacja.model.*;
import symulacja.ui.MapaPanel;

import java.util.*;

public class Main {
    private static Map<String, Lotnisko> lotniska;
    private static List<Lot> wszystkieLoty;

    public static void main(String[] args) {
        symulacja.ui.OknoGlowne.wyswietlOkno();
        testSystemuPogodowego();
    }

    public static List<Lot> wygenerujLoty() {
        if (wszystkieLoty == null) {
            wszystkieLoty = nowaSymulacja();
        }
        return wszystkieLoty;
    }

    private static List<Lot> nowaSymulacja() {
        Klimat europa = new KlimatUmiarkowany();
        List<String> miasta = new ArrayList<>(MapaPanel.getStolice().keySet());

        lotniska = new HashMap<>();
        for (String miasto : miasta) {
            lotniska.put(miasto, new Lotnisko(miasto, europa));
        }

        List<Lot> wygenerowaneLoty = new ArrayList<>();
        List<String> dniTygodnia = List.of("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela");
        Random rand = new Random();

        for (String dzien : dniTygodnia) {
            for (int i = 0; i < 30; i++) {
                Lotnisko start = losoweLotnisko(lotniska.values(), rand);
                String skad = start.getNazwa();

                String dokad;
                do {
                    dokad = miasta.get(rand.nextInt(miasta.size()));
                } while (dokad.equals(skad));

                boolean towarowy = rand.nextDouble() < 0.15;
                Samolot samolot;
                if (towarowy) {
                    samolot = new SamolotTowarowy("CT-" + UUID.randomUUID());
                } else {
                    int liczbaMiejsc = 90 + rand.nextInt(71); // 90–160
                    samolot = new SamolotPasazerski("SP-" + UUID.randomUUID(), liczbaMiejsc);
                }

                Lot lot = new Lot(samolot, skad, dokad);
                lot.setDzienTygodnia(dzien);
                lot.setLotniskoStartowe(start);
                lot.wygenerujZaloge(towarowy ? 3 : 7);
                lot.sprawdzPogode(europa);

                if (!towarowy && !lot.isOdwolany()) {
                    int maxMiejsc = samolot.getLiczbaMiejsc();
                    int ilePasazerow = maxMiejsc - rand.nextInt(4); // prawie pełny samolot
                    for (int j = 0; j < ilePasazerow; j++) {
                        String paszport = Osoba.generujNumerPaszportu("PAS");
                        Pasazer p = new Pasazer(paszport, skad, dokad);
                        p.setDzienLotu(dzien);
                        lot.zarezerwujMiejsce(p);
                    }
                }

                lot.zakonczLot(start);
                start.dodajLot(dzien, lot);
                wygenerowaneLoty.add(lot);
            }
        }
        return wygenerowaneLoty;
    }

    private static void testSystemuPogodowego() {
        List<Lot> loty = wygenerujLoty();
        List<String> dniTygodnia = List.of("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela");

        int liczbaTowarowych = 0;
        int liczbaWszystkich = 0;
        int liczbaOdwolanych = 0;
        Map<String, Lotnisko> unikalneLotniska = new HashMap<>();

        System.out.println("\n=== Rozpoczęcie testu systemu pogodowego (podział na dni) ===");

        for (String dzien : dniTygodnia) {
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
                        System.out.println(">>> LOT ODWOLANY! <<< Powód: " + lot.getPogoda().getOpis());
                        liczbaOdwolanych++;
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
    }

    private static Lotnisko losoweLotnisko(Collection<Lotnisko> lotniska, Random rand) {
        List<Lotnisko> lista = new ArrayList<>(lotniska);
        return lista.get(rand.nextInt(lista.size()));
    }
}
