package dev.nikee.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BossBarUtil {

    private static final Map<UUID, Map<String, BossBar>> bossBars = new HashMap<>();
    private static JavaPlugin plugin;

    public static void init(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
    }

    public static void add(Player player, String id, String title, BarColor color, BarStyle style, double startProgress, boolean autoUpdate, int timeSeconds) {
        remove(player, id);

        BossBar bossBar = Bukkit.createBossBar(TextUtil.format(title.replace("{{progressTime}}", formatDuration(timeSeconds))), color, style);
        bossBar.setProgress(clamp(startProgress));
        bossBar.setVisible(true);
        bossBar.addPlayer(player);

        bossBars.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(id, bossBar);

        if (autoUpdate && timeSeconds > 0) {
            double step = clamp(startProgress) / timeSeconds;

            new BukkitRunnable() {
                double progress = startProgress;
                int secondsLeft = timeSeconds;

                @Override
                public void run() {
                    progress -= step;
                    secondsLeft--;

                    if (progress <= 0 || secondsLeft < 0) {
                        remove(player, id);
                        cancel();
                        return;
                    }

                    BossBar bar = get(player, id);
                    if (bar != null) {
                        bar.setProgress(clamp(progress));
                        String updatedTitle = TextUtil.format(title.replace("{{progressTime}}", formatDuration(secondsLeft)));
                        bar.setTitle(updatedTitle);
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 20L, 20L);
        }
    }

    public static void remove(Player player, String id) {
        UUID uuid = player.getUniqueId();
        Map<String, BossBar> playerBars = bossBars.get(uuid);
        if (playerBars != null) {
            BossBar bar = playerBars.remove(id);
            if (bar != null) {
                bar.removeAll();
            }
            if (playerBars.isEmpty()) {
                bossBars.remove(uuid);
            }
        }
    }

    public static void removeAll(Player player) {
        UUID uuid = player.getUniqueId();
        Map<String, BossBar> playerBars = bossBars.remove(uuid);
        if (playerBars != null) {
            for (BossBar bar : playerBars.values()) {
                bar.removeAll();
            }
        }
    }

    public static void update(Player player, String id, String title, double progress) {
        BossBar bar = get(player, id);
        if (bar != null) {
            bar.setTitle(TextUtil.format(title));
            bar.setProgress(clamp(progress));
        }
    }

    public static boolean has(Player player, String id) {
        Map<String, BossBar> playerBars = bossBars.get(player.getUniqueId());
        return playerBars != null && playerBars.containsKey(id);
    }

    public static void setVisible(Player player, String id, boolean visible) {
        BossBar bar = get(player, id);
        if (bar != null) {
            bar.setVisible(visible);
        }
    }

    private static BossBar get(Player player, String id) {
        Map<String, BossBar> playerBars = bossBars.get(player.getUniqueId());
        if (playerBars == null) return null;
        return playerBars.get(id);
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }

    private static String formatDuration(long seconds) {
        long years = seconds / 31_536_000L;
        long months = (seconds % 31_536_000L) / 2_592_000L;
        long days = (seconds % 2_592_000L) / 86_400L;
        long hours = (seconds % 86_400L) / 3_600L;
        long minutes = (seconds % 3_600L) / 60L;
        long remainingSeconds = seconds % 60L;

        if (years > 0) return String.format("%dy %dm %dd", years, months, days);
        if (months > 0) return String.format("%dm %dd %dh", months, days, hours);
        if (days > 0) return String.format("%dd %dh %dm", days, hours, minutes);
        if (hours > 0) return String.format("%dh %dm %ds", hours, minutes, remainingSeconds);
        if (minutes > 0) return String.format("%dm %ds", minutes, remainingSeconds);
        return String.format("%ds", remainingSeconds);
    }
}
