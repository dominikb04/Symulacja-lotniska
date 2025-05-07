package symulacja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasazerLoader {
    private static final Random random = new Random();

    public static List<Pasazer> generujLosowychPasazerow(int liczba, List<String> miasta, List<String> dniTygodnia) {
        List<Pasazer> pasazerowie = new ArrayList<>();

        for (int i = 0; i < liczba; i++) {
            String numerPaszportu = generujNumerPaszportu();

            // Losowe miasta
            String skad = miasta.get(random.nextInt(miasta.size()));
            String dokad;
            do {
                dokad = miasta.get(random.nextInt(miasta.size()));
            } while (dokad.equals(skad));

            // Losowy dzień
            String dzienLotu = dniTygodnia.get(random.nextInt(dniTygodnia.size()));

            Pasazer pasazer = new Pasazer(numerPaszportu, skad, dokad, dzienLotu);
            pasazerowie.add(pasazer);
        }

        return pasazerowie;
    }

    private static String generujNumerPaszportu() {
        return "P" + (100000 + random.nextInt(900000)); // np. P123456
    }
}
