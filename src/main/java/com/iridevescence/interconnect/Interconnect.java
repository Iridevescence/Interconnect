package com.iridevescence.interconnect;

import java.nio.file.Path;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import com.iridevescence.interconnect.command.RestrictionCommand;
import com.iridevescence.interconnect.event.InterconnectItemEvents;
import com.iridevescence.interconnect.event.InterconnectBlockEvents;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public final class Interconnect extends JavaPlugin {
    public static Interconnect INSTANCE;
    public static Logger LOGGER;
    public static Path DATA_DIR;

    public static Interconnect YOURPLUGININSTANCEREPLACETHISNOWPRISMDOIT;

    public static final HashMap<String, HashMap<String, String>> TRANSLATIONS = new HashMap<>();

    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
  
    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        INSTANCE = Interconnect.getPlugin(Interconnect.class);
        LOGGER = INSTANCE.getSLF4JLogger();
        DATA_DIR = INSTANCE.getDataFolder().toPath();
        this.adventure = BukkitAudiences.create(this);
        Bukkit.getPluginManager().registerEvents(new InterconnectItemEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InterconnectBlockEvents(), this);
        this.getCommand("restriction").setExecutor(new RestrictionCommand());
        YOURPLUGININSTANCEREPLACETHISNOWPRISMDOIT = INSTANCE;
    }
  
    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
