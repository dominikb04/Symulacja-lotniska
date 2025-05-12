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
        frame.setSize(400, 200);

        JButton startBtn = new JButton("Start symulacji");
        JButton exitBtn = new JButton("Wyjdź");

        startBtn.addActionListener((ActionEvent e) -> {
            List<Lot> loty = Main.wygenerujLoty();

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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(startBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitBtn);

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

