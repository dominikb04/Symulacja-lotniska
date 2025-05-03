package symulacja.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * Panel rysujący mapę Europy jako tło.
 */
public class MapaPanel extends JPanel {
    private BufferedImage mapa;
    Map<String, Point> stolice = new HashMap<>(Map.ofEntries(
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

    public MapaPanel() {
        try {
            mapa = ImageIO.read(getClass().getResource("/europa.png"));
            setPreferredSize(new Dimension(mapa.getWidth(), mapa.getHeight()));
        } catch (IOException e) {
            System.err.println("Nie udało się wczytać mapy.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // rzutowanie do Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // włączenie antyaliasingu - usuwanie postrzepionych krawedzi na grafice tworzonej w kodzie

        super.paintComponent(g);
        if (mapa != null) {
            g.drawImage(mapa, 0, 0, this);
            for (Point punkt : stolice.values()) {
                g.setColor(Color.BLACK);
                g.fillOval(punkt.x - 5, punkt.y - 5, 10, 10); // -5 żeby środek punktu był w miejscu stolicy
            }

        }
    }
}
