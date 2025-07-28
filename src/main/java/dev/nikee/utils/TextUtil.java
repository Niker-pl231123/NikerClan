package dev.nikee.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("(&#([0-9a-fA-F]{6}))");

    public static void sendColoredMessage(CommandSender sender, String message) {
        if (sender != null && message != null) {
            sender.sendMessage(format((message)));
        }
    }

    public static String format(String text) {
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
}
