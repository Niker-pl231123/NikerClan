package dev.nikee.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)(r|mies|d|h|m|s)");

    public static long parseToMillis(String input) {
        long millis = 0;

        Matcher matcher = TIME_PATTERN.matcher(input.toLowerCase());

        int totalYears = 0;
        int totalMonths = 0;
        int totalDays = 0;
        int totalHours = 0;
        int totalMinutes = 0;
        int totalSeconds = 0;

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "r": totalYears += value; break;
                case "mies": totalMonths += value; break;
                case "d": totalDays += value; break;
                case "h": totalHours += value; break;
                case "m": totalMinutes += value; break;
                case "s": totalSeconds += value; break;
                default:
                    throw new IllegalArgumentException("Użyj: r - rok, mies - miesiąc, d - dzień, h - godzina, m - minuta, s - sekunda");
            }
        }

        totalYears += totalMonths / 12;
        totalMonths = totalMonths % 12;

        millis += totalYears * 365L * 24 * 60 * 60 * 1000;
        millis += totalMonths * 30L * 24 * 60 * 60 * 1000;
        millis += totalDays * 24L * 60 * 60 * 1000;
        millis += totalHours * 60L * 60 * 1000;
        millis += totalMinutes * 60L * 1000;
        millis += totalSeconds * 1000L;

        if (millis <= 0) {
            throw new IllegalArgumentException("Użyj: r - rok, mies - miesiąc, d - dzień, h - godzina, m - minuta, s - sekunda");
        }

        return millis;
    }

    private static String pluralize(int count, String form1, String form2, String form5) {
        int absCount = Math.abs(count);
        int mod10 = absCount % 10;
        int mod100 = absCount % 100;

        if (absCount == 1) {
            return form1;
        } else if (mod10 >= 2 && mod10 <= 4 && (mod100 < 12 || mod100 > 14)) {
            return form2;
        } else {
            return form5;
        }
    }

    public static String formatDuration(long millis) {
        if (millis == Long.MAX_VALUE) return "perm";
        if (millis <= 0) return "null";

        long totalSeconds = millis / 1000;

        long years = totalSeconds / (365L * 24 * 60 * 60);
        totalSeconds %= 365L * 24 * 60 * 60;

        long months = totalSeconds / (30L * 24 * 60 * 60);
        totalSeconds %= 30L * 24 * 60 * 60;

        if (months >= 12) {
            years += months / 12;
            months = months % 12;
        }

        long days = totalSeconds / (24 * 60 * 60);
        totalSeconds %= 24 * 60 * 60;

        long hours = totalSeconds / (60 * 60);
        totalSeconds %= 60 * 60;

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();

        if (years > 0) {
            sb.append(years).append(years == 1 ? " rok " : " lata ");
            if (months > 0) sb.append(months).append(" mies ");
            if (days > 0) sb.append(days).append("d ");
            if (hours > 0) sb.append(hours).append("h ");
        } else if (months > 0) {
            sb.append(months).append(" mies ");
            if (days > 0) sb.append(days).append("d ");
            if (hours > 0) sb.append(hours).append("h ");
            if (minutes > 0) sb.append(minutes).append("m ");
        } else if (days > 0) {
            sb.append(days).append("d ");
            if (hours > 0) sb.append(hours).append("h ");
            if (minutes > 0) sb.append(minutes).append("m ");
        } else if (hours > 0) {
            sb.append(hours).append("h ");
            if (minutes > 0) sb.append(minutes).append("m ");
            if (seconds > 0) sb.append(seconds).append("s ");
        } else if (minutes > 0) {
            sb.append(minutes).append("m ");
            if (seconds > 0) sb.append(seconds).append("s ");
        } else {
            sb.append(seconds).append("s");
        }

        return sb.toString().trim();
    }

    public static String safeFormat(String input) {
        try {
            long millis;
            if (input.matches("\\d+")) {
                millis = Long.parseLong(input);
            } else {
                millis = parseToMillis(input);
            }
            return formatDuration(millis);
        } catch (Exception e) {
            return "§cNieprawidłowy format czasu!";
        }
    }

    public static String getFormatInfo() {
        return "";
    }
}
