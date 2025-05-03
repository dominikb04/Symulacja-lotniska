package symulacja.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.swing.Timer;
/**
 * Panel rysujący mapę Europy i animacje przelotów samolotów.
 */
public class MapaPanel extends JPanel {
    private BufferedImage mapa;
    private BufferedImage ikonaSamolotu;
    private final Map<String, Point> stolice = new HashMap<>(Map.ofEntries(
            Map.entry("Warszawa", new Point(526, 463)),
            Map.entry("Berlin", new Point(416, 464)),
            Map.entry("Paryż", new Point(260, 550)),
            Map.entry("Rzym", new Point(408, 708)),
            Map.entry("Madryt", new Point(110, 747)),
            Map.entry("Londyn", new Point(221, 483)),
            Map.entry("Oslo", new Point(375, 294)),
            Map.entry("Kopenhaga", new Point(401, 394)),
            Map.entry("Amsterdam", new Point(297, 471)),
            Map.entry("Bruksela", new Point(285, 504)),
            Map.entry("Praga", new Point(436, 526)),
            Map.entry("Wiedeń", new Point(467, 567)),
            Map.entry("Bratysława", new Point(482, 564)),
            Map.entry("Budapeszt", new Point(511, 582)),
            Map.entry("Bukareszt", new Point(636, 622)),
            Map.entry("Ateny", new Point(622, 781)),
            Map.entry("Sofia", new Point(601, 672)),
            Map.entry("Zagrzeb", new Point(470, 620)),
            Map.entry("Sarajewo", new Point(511, 665)),
            Map.entry("Podgorica", new Point(534, 695)),
            Map.entry("Skopje", new Point(573, 702)),
            Map.entry("Tirana", new Point(546, 720)),
            Map.entry("Belgrad", new Point(546, 635)),
            Map.entry("Tallin", new Point(545, 292)),
            Map.entry("Ryga", new Point(550, 349)),
            Map.entry("Wilno", new Point(577, 399)),
            Map.entry("Sztokholm", new Point(462, 311)),
            Map.entry("Berno", new Point(392, 599)),
            Map.entry("Ankara", new Point(771, 700)),
            Map.entry("Helsinki", new Point(541, 272)),
            Map.entry("Dublin", new Point(146, 424)),
            Map.entry("Reykjavik", new Point(66, 137)),
            Map.entry("Moskwa", new Point(760, 380)),
            Map.entry("Kijów", new Point(674, 474)),
            Map.entry("Mińsk", new Point(608, 408)),
            Map.entry("Kiszyniów", new Point(674, 566)),
            Map.entry("Lizbona", new Point(28, 752))
    ));

    private final java.util.List<FlightAnimation> activeFlights = new ArrayList<>();
    private final javax.swing.Timer timer;

    public MapaPanel() {
        setPreferredSize(new Dimension(1024, 876)); // Ustawienie preferowanego rozmiaru panelu
        try {
            mapa = ImageIO.read(getClass().getResource("/europa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ikonaSamolotu = ImageIO.read(getClass().getResource("/ikonkaS.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dodajemy testowy lot przy starcie
        SwingUtilities.invokeLater(() -> dodajPrzelot("Warszawa", "Paryż"));
        SwingUtilities.invokeLater(() -> dodajPrzelot("Budapeszt", "Londyn"));
        SwingUtilities.invokeLater(() -> dodajPrzelot("Oslo", "Reykjavik"));
        SwingUtilities.invokeLater(() -> dodajPrzelot("Sztokholm", "Ankara"));

        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Iterator<FlightAnimation> it = activeFlights.iterator(); it.hasNext(); ) {
                    FlightAnimation flight = it.next();
                    if (!flight.updatePosition()) {
                        it.remove();
                    }
                }
                repaint();
            }
        });
        timer.start();
    }

    public void dodajPrzelot(String z, String do_) {
        Point start = stolice.get(z);
        Point end = stolice.get(do_);
        if (start != null && end != null) {
            activeFlights.add(new FlightAnimation(start, end));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mapa != null) {
            g2d.drawImage(mapa, 0, 0, this);

            // Rysowanie stolic jako czarne kropki
            g2d.setColor(Color.BLACK);
            for (Point punkt : stolice.values()) {
                g2d.fillOval(punkt.x - 5, punkt.y - 5, 10, 10);
            }
        }

        if (ikonaSamolotu != null) {
            for (FlightAnimation flight : activeFlights) {
                int x = (int) flight.current.x - ikonaSamolotu.getWidth() / 2;
                int y = (int) flight.current.y - ikonaSamolotu.getHeight() / 2;
                g2d.drawImage(ikonaSamolotu, x, y, null);
            }
        }
    }

    private static class FlightAnimation {
        private final Point2D.Double current;
        private final double dx, dy;
        private final int steps = 100;
        private int step = 0;

        public FlightAnimation(Point start, Point end) {
            this.current = new Point2D.Double(start.x, start.y);
            this.dx = (end.x - start.x) / (double) steps;
            this.dy = (end.y - start.y) / (double) steps;
        }

        public boolean updatePosition() {
            if (step++ < steps) {
                current.x += dx;
                current.y += dy;
                return true;
            }
            return false;
        }
    }
}
