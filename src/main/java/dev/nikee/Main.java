package dev.nikee;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        //
        Start startUp = new Start();
        startUp.start();
    }

    @Override
    public void onDisable() {
        Start startUp = new Start();
        startUp.stop();
    }

    public static Main getInstance() {
        return instance;
    }
}
