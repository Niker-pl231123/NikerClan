package dev.nikee.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Title {

    private static final Pattern HEX_PATTERN = Pattern.compile("(&#([0-9a-fA-F]{6}))");

    public static String send(String text) {
        if (text == null) {
            return "";
        }
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = matcher.group(2);
            try {
                ChatColor color = ChatColor.of("#" + hex);
                matcher.appendReplacement(sb, color.toString());
            } catch (IllegalArgumentException e) {
                matcher.appendReplacement(sb, matcher.group(1));
            }
        }
        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        String coloredTitle = send(title);
        String coloredSubtitle = send(subtitle);
        player.sendTitle(coloredTitle, coloredSubtitle, 5, 35, 7);
    }

    public static void sendTitle(Player player, String text, String subtitle, int fadeIn, int stay, int fadeOut) {
        String colored = send(text);
        String coloredSubtitle = send(subtitle);
        player.sendTitle(colored, coloredSubtitle, fadeIn, stay, fadeOut);
    }
}