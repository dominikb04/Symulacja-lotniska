package symulacja.ui;

import symulacja.model.Lot;
import symulacja.model.SamolotTowarowy;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MapaPanel extends JPanel {
    private BufferedImage mapa;
    private BufferedImage ikonaSamolotu;
    private BufferedImage ikonaTowarowa;
    private BufferedImage ikonaSamolotuOdwolany;
    private BufferedImage ikonaTowarowaOdwolany;
    private final List<Lot> wszystkieLoty;
    private final List<FlightAnimation> activeFlights = new ArrayList<>();
    private final Timer timer;

    private final List<String> dniTygodnia = List.of(
            "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"
    );
    private int indeksDnia = 0;
    private String aktualnyDzien = dniTygodnia.get(0);
    private static final Map<String, Point> stolice = new HashMap<>(Map.ofEntries(
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

    public static Map<String, Point> getStolice() {
        return Collections.unmodifiableMap(new HashMap<>(stolice));
    }

    public MapaPanel(List<Lot> lotyZSymulacji) {
        this.wszystkieLoty = lotyZSymulacji;
        setPreferredSize(new Dimension(1024, 876));

        try {
            mapa = ImageIO.read(getClass().getResource("/europa.png"));
            ikonaSamolotu = ImageIO.read(getClass().getResource("/ikonkaS.png"));
            ikonaTowarowa = ImageIO.read(getClass().getResource("/ikonkaT.png"));
            ikonaSamolotuOdwolany = ImageIO.read(getClass().getResource("/ikonkaSO.png"));
            ikonaTowarowaOdwolany = ImageIO.read(getClass().getResource("/ikonkaTO.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> wyswietlDzien(dniTygodnia.get(indeksDnia)));

        timer = new Timer(30, e -> {
            for (Iterator<FlightAnimation> it = activeFlights.iterator(); it.hasNext(); ) {
                FlightAnimation flight = it.next();
                if (!flight.updatePosition()) {
                    it.remove();
                }
            }
            repaint();
        });
        timer.start();
    }

    public void nextDay() {
        indeksDnia = (indeksDnia + 1) % dniTygodnia.size();
        aktualnyDzien = dniTygodnia.get(indeksDnia);
        wyswietlDzien(aktualnyDzien);
    }

    public void wyswietlDzien(String dzien) {
        activeFlights.clear();

        for (Lot lot : wszystkieLoty) {
            String dzienLotu = lot.getDzienTygodnia();
            if (dzienLotu != null && dzien.equalsIgnoreCase(dzienLotu)) {
                dodajPrzelot(lot);
            }
        }
        repaint();
    }

    public void dodajPrzelot(Lot lot) {
        Point start = stolice.get(lot.getStart());
        Point end = stolice.get(lot.getCel());
        boolean towarowy = lot.getSamolot() instanceof SamolotTowarowy;
        if (start != null && end != null) {
            activeFlights.add(new FlightAnimation(lot, start, end, towarowy));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mapa != null) {
            g2d.drawImage(mapa, 0, 0, this);
            g2d.setColor(Color.BLACK);
            for (Point punkt : stolice.values()) {
                g2d.fillOval(punkt.x - 5, punkt.y - 5, 10, 10);
            }
            for (Map.Entry<String, Point> entry : stolice.entrySet()) {
                g2d.drawString(entry.getKey(), entry.getValue().x, entry.getValue().y - 10);
            }
        }

        for (FlightAnimation flight : activeFlights) {
            Lot lot = flight.getLot();
            boolean odwolany = lot.isOdwolany();

            BufferedImage ikona;
            if (flight.isTowarowy()) {
                ikona = odwolany ? ikonaTowarowaOdwolany : ikonaTowarowa;
            } else {
                ikona = odwolany ? ikonaSamolotuOdwolany : ikonaSamolotu;
            }

            Point2D.Double pos = flight.getCurrent();
            int x = (int) pos.x - ikona.getWidth() / 2;
            int y = (int) pos.y - ikona.getHeight() / 2;
            g2d.drawImage(ikona, x, y, null);
        }
        // Wyświetlanie aktualnego dnia tygodnia na górze mapy
        g2d.setColor(new Color(255, 255, 255, 200)); // lekko przezroczyste tło
        g2d.fillRoundRect(400, 10, 220, 30, 15, 15);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(aktualnyDzien);
        g2d.drawString(aktualnyDzien, 512 - textWidth / 2, 32);
    }

    private static class FlightAnimation {
        private final Point2D.Double current;
        private final double dx, dy;
        private final int steps = 100;
        private int step = 0;
        private final boolean towarowy;
        private final Lot lot;

        public FlightAnimation(Lot lot, Point start, Point end, boolean towarowy) {
            this.lot = lot;
            this.current = new Point2D.Double(start.x, start.y);
            this.dx = (end.x - start.x) / (double) steps;
            this.dy = (end.y - start.y) / (double) steps;
            this.towarowy = towarowy;
        }

        public Lot getLot() {
            return lot;
        }

        public boolean updatePosition() {
            if (step++ < steps) {
                current.x += dx;
                current.y += dy;
                return true;
            }
            return false;
        }

        public Point2D.Double getCurrent() {
            return current;
        }

        public boolean isTowarowy() {
            return towarowy;
        }
    }
}