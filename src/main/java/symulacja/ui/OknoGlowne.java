package symulacja.ui;
import symulacja.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Główne okno aplikacji (Swing).
 * Zawiera dwa przyciski – start symulacji i wyjście z aplikacji.
 */
public class OknoGlowne {

    public static void wyswietlOkno() {
        JFrame frame = new JFrame("Symulacja Lotniska");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Panel z przyciskami
        JButton startBtn = new JButton("Start symulacji");
        JButton exitBtn = new JButton("Wyjdź");

        startBtn.addActionListener((ActionEvent e) -> {
            // === Symulacja lotu ===

            // 1. Tworzymy samolot i lot
            SamolotPasazerski samolot = new SamolotPasazerski("SP-LOT01", 2);
            Lot lot = new Lot(samolot, "Warszawa", "Londyn");

            // 2. Tworzymy dwóch pasażerów
            Pasazer p1 = new Pasazer("Marta", "Kwiatkowska", "PAS001");
            Pasazer p2 = new Pasazer("Tomasz", "Lewandowski", "PAS002");

            // 3. Dokonujemy rezerwacji
            StringBuilder sb = new StringBuilder("Symulacja rezerwacji z " + lot.getStart() + " do " + lot.getCel() + ":\n\n");

            if (lot.zarezerwujMiejsce(p1)) {
                Rezerwacja r1 = new Rezerwacja(p1, lot);
                p1.dodajRezerwacje(r1);
                sb.append("✓ Rezerwacja dla ").append(p1.getPelneImie()).append(" - OK\n");
            } else {
                sb.append("✗ Brak miejsca dla ").append(p1.getPelneImie()).append("\n");
            }

            if (lot.zarezerwujMiejsce(p2)) {
                Rezerwacja r2 = new Rezerwacja(p2, lot);
                p2.dodajRezerwacje(r2);
                sb.append("✓ Rezerwacja dla ").append(p2.getPelneImie()).append(" - OK\n");
            } else {
                sb.append("✗ Brak miejsca dla ").append(p2.getPelneImie()).append("\n");
            }

            sb.append("\nLista pasażerów na pokładzie:\n");
            for (Pasazer p : lot.getPasazerowie()) {
                sb.append("• ").append(p.getPelneImie()).append(" (").append(p.getNumerPaszportu()).append(")\n");
            }

            // 4. Wyświetlamy wynik w GUI
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        exitBtn.addActionListener((ActionEvent e) -> System.exit(0));

        // Layout: wyśrodkowane przyciski
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80)); // marginesy
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(startBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // odstęp
        panel.add(exitBtn);

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null); // wyśrodkowanie okna
        frame.setVisible(true);
    }
}



