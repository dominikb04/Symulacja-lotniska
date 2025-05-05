package symulacja.model;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za wczytywanie danych pasażerów z pliku tekstowego znajdującego się w folderze resources.
 */
public class PasazerLoader {

    /**
     * Wczytuje pasażerów z pliku zasobów.
     * Oczekiwany format linii:
     * Imie,Nazwisko,NrPaszportu,Skad,Dokad,DzienLotu
     *
     * @param sciezka ścieżka do pliku wewnątrz folderu resources, np. "pasazerowie.txt"
     * @return lista obiektów Pasazer
     */

    public static List<Pasazer> wczytajPasazerow(String sciezka) {
        List<Pasazer> pasazerowie = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(PasazerLoader.class.getClassLoader().getResourceAsStream(sciezka), StandardCharsets.UTF_8))) {
            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dane = linia.split(",");
                if (dane.length == 6) {
                    pasazerowie.add(new Pasazer(dane[0], dane[1], dane[2], dane[3], dane[4], dane[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pasazerowie;
    }
}
