package symulacja.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa bazowa dla wszystkich osób w systemie (pasażerowie, załoga).
 */
public abstract class Osoba {
    private String numerPaszportu;
    private static final AtomicInteger licznikPaszportow = new AtomicInteger(1);

    public Osoba(String numerPaszportu) {
        this.numerPaszportu = numerPaszportu;
    }

    public String getNumerPaszportu() {
        return numerPaszportu;
    }

    public static String generujNumerPaszportu(String prefix) {
        int numer = licznikPaszportow.getAndIncrement();
        return prefix + String.format("-%04d", numer);
    }
    @Override
    public String toString() {
        return "Paszport: " + numerPaszportu;
    }
}