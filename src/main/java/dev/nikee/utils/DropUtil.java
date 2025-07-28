package dev.nikee.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropUtil {
    public DropUtil() {
    }

    public static void giveItem(Player player, ItemStack itemStack) {
        if (isInventoryFull(player)) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(new ItemStack[]{itemStack});
        }

    }

    private static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
