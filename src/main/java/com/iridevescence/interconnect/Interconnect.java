package com.iridevescence.interconnect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.iridevescence.interconnect.event.InterconnectBlockEvents;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public final class Interconnect extends JavaPlugin {
    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
      if(this.adventure == null) {
        throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
      }
      return this.adventure;
    }
  
    @Override
    public void onEnable() {
      this.adventure = BukkitAudiences.create(this);
      Bukkit.getPluginManager().registerEvents(new InterconnectBlockEvents(), this);
    }
  
    @Override
    public void onDisable() {
      if(this.adventure != null) {
        this.adventure.close();
        this.adventure = null;
      }
    }
}
