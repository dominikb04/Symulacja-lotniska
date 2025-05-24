package symulacja.ui;

import symulacja.Main;
import symulacja.model.Lot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OknoGlowne {

    public static void wyswietlOkno() {
        JFrame frame = new JFrame("Symulacja Lotniska");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTextField liczbaLotowPole = new JTextField("500", 5); // domyślnie 500
        JButton wygenerujButton = new JButton("Generuj loty");
        JButton startBtn = new JButton("Start symulacji");
        JButton exitBtn = new JButton("Wyjdź");

        // Panel kontrolny na górze
        JPanel panelKontrolny = new JPanel(new FlowLayout());
        panelKontrolny.add(new JLabel("Liczba lotów tygodniowo:"));
        panelKontrolny.add(liczbaLotowPole);
        panelKontrolny.add(wygenerujButton);

        // Panel przycisków głównych
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(startBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitBtn);

        // Obsługa przycisku „Generuj loty”
        wygenerujButton.addActionListener(e -> {
            try {
                int liczbaLotow = Integer.parseInt(liczbaLotowPole.getText());
                if (liczbaLotow < 7) {
                    JOptionPane.showMessageDialog(frame, "Minimalna liczba lotów to 7.");
                    return;
                }
                Main.generujLotyNaTydzien(liczbaLotow);
                JOptionPane.showMessageDialog(frame, "Wygenerowano " + liczbaLotow + " lotów.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Podaj poprawną liczbę.");
            }
        });

        // Obsługa przycisku „Start symulacji”
        startBtn.addActionListener((ActionEvent e) -> {
            List<Lot> loty = Main.wszystkieLoty;

            JFrame mapaFrame = new JFrame("Mapa Europy");
            mapaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mapaFrame.setSize(1024, 920);
            mapaFrame.setResizable(false);

            MapaPanel mapaPanel = new MapaPanel(loty);
            JButton nextDayBtn = new JButton("Następny dzień");

            nextDayBtn.addActionListener(e2 -> mapaPanel.nextDay());

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(mapaPanel, BorderLayout.CENTER);
            mainPanel.add(nextDayBtn, BorderLayout.SOUTH);

            mapaFrame.getContentPane().add(mainPanel);
            mapaFrame.pack();
            mapaFrame.setLocationRelativeTo(null);
            mapaFrame.setVisible(true);
        });

        exitBtn.addActionListener((ActionEvent e) -> System.exit(0));

        // Dodaj wszystko do głównego okna
        frame.getContentPane().add(panelKontrolny, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
