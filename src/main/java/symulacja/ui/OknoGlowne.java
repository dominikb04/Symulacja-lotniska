package symulacja.ui;

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
            JOptionPane.showMessageDialog(frame, "Symulacja jeszcze niezaimplementowana.");
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



