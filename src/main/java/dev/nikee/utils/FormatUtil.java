package dev.nikee.utils;

import java.text.DecimalFormat;

public class FormatUtil {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static String format(int amount) {
        if ((double)amount < 1000.0) {
            return String.valueOf(amount);
        }
        int exp = (int)(Math.log(amount) / Math.log(1000.0));
        String[] units = new String[]{"", "k", "MLN", "MLD", "B", "BLD", "QD", "QN", "SX", "SP", " OC", "NIL", "DIL", "UND", "DUO", "TRE", "QUA", "QUI", "SXD", "SPD", "OCD", "NVD", "VIG", "UNV", "DVI", "TRV", "QUT", "QUN", "SXV", "SPV", "OCV", "NOG", "TRI", "UTR", "DUT", "TDU", "CET"};
        if (exp >= units.length) {
            exp = units.length - 1;
        }
        double value = (double)amount / Math.pow(1000.0, exp);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String formattedValue = decimalFormat.format(value);
        return formattedValue + units[exp];
    }

    public static String format(double amount) {
        if (amount < 1000.0) {
            return df.format(amount);
        }
        int exp = (int)(Math.log(amount) / Math.log(1000.0));
        String[] units = new String[]{"", "k", "MLN", "MLD", "B", "BLD", "QD", "QN", "SX", "SP", " OC", "NIL", "DIL", "UND", "DUO", "TRE", "QUA", "QUI", "SXD", "SPD", "OCD", "NVD", "VIG", "UNV", "DVI", "TRV", "QUT", "QUN", "SXV", "SPV", "OCV", "NOG", "TRI", "UTR", "DUT", "TDU", "CET"};
        if (exp >= units.length) {
            exp = units.length - 1;
        }
        double value = amount / Math.pow(1000.0, exp);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String formattedValue = decimalFormat.format(value);
        return formattedValue + units[exp];
    }
}