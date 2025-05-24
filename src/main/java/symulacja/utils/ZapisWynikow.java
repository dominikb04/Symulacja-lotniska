package symulacja.utils;

import symulacja.model.Lot;
import symulacja.model.Lotnisko;
import symulacja.model.Pasazer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class ZapisWynikow {

    public static void zapiszWyniki(List<Lot> loty, Collection<Lotnisko> lotniska, String sciezkaPliku) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(sciezkaPliku, false))) {

            // 1. Liczba lotow dziennie
            writer.println("=== LICZBA LOTÓW KAŻDEGO DNIA ===");
            Map<String, Long> lotyNaDzien = loty.stream()
                    .collect(Collectors.groupingBy(Lot::getDzienTygodnia, LinkedHashMap::new, Collectors.counting()));
            lotyNaDzien.forEach((dzien, liczba) -> writer.printf("%s: %d%n", dzien, liczba));
            writer.println();

            // 2. Informacje o lotach
            writer.println("=== SZCZEGÓŁY LOTÓW ===");
            for (Lot lot : loty) {
                writer.printf("[%s] %s -> %s | Pasażerowie: %d/%d [LOT: %d]%n",
                        lot.getDzienTygodnia(),
                        lot.getStart(),
                        lot.getCel(),
                        lot.getPasazerowie().size(),
                        lot.getSamolot().getLiczbaMiejsc(),
                        lot.hashCode());

                if (!lot.getNiewpuszczeni().isEmpty()) {
                    writer.println("Nie zmieścili się pasażerowie (numery paszportów):");
                    for (Pasazer p : lot.getNiewpuszczeni()) {
                        writer.println("- " + p.getNumerPaszportu());
                    }
                }
                writer.println();
            }

            // 3. Loty odwołane
            writer.println("=== LOTY ODWOŁANE ===");
            List<Lot> odwolane = loty.stream()
                    .filter(Lot::isOdwolany)
                    .collect(Collectors.toList());
            writer.printf("Liczba odwołanych lotów: %d%n", odwolane.size());
            for (Lot lot : odwolane) {
                writer.printf("[LOT: %d] %s -> %s (%s)%n",
                        lot.hashCode(),
                        lot.getStart(),
                        lot.getCel(),
                        lot.getDzienTygodnia());
            }
            writer.println();

            // 4. Budżety lotnisk
            writer.println("=== BUDŻETY LOTNISK ===");
            List<Lotnisko> posortowane = new ArrayList<>(lotniska);
            posortowane.sort(Comparator.comparingDouble(Lotnisko::getBudzet).reversed());

            for (Lotnisko lotnisko : posortowane) {
                writer.printf("%s: %.2f zł%n", lotnisko.getNazwa(), lotnisko.getBudzet());
            }

            if (!posortowane.isEmpty()) {
                writer.println();
                writer.printf("Najlepiej zarabiające lotnisko: %s (%.2f zł)%n",
                        posortowane.get(0).getNazwa(), posortowane.get(0).getBudzet());
                writer.printf("Najgorzej zarabiające lotnisko: %s (%.2f zł)%n",
                        posortowane.get(posortowane.size() - 1).getNazwa(),
                        posortowane.get(posortowane.size() - 1).getBudzet());
            }

            // 5. Najbardziej obciążona trasa
            writer.println();
            writer.println("=== NAJBARDZIEJ OBCIĄŻONA TRASA ===");
            Map<String, Long> trasy = loty.stream()
                    .collect(Collectors.groupingBy(
                            l -> l.getStart() + " -> " + l.getCel(),
                            Collectors.counting()
                    ));
            trasy.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .ifPresent(entry -> writer.printf("%s | liczba lotów: %d %n", entry.getKey(), entry.getValue()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
