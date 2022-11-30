package com.iridevescence.interconnect.util.player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.iridevescence.interconnect.Interconnect;

public class InterconnectPlayerManager {
    public static final Path PERK_DATA_DIR = Path.of(Interconnect.DATA_DIR + "/perks/");

    public static void save(Player player) {
        if (!PERK_DATA_DIR.toFile().exists()) {
            PERK_DATA_DIR.toFile().mkdirs();
        }
        UUID uuid = player.getUniqueId();
        File data = Path.of(PERK_DATA_DIR + "/" + uuid).toFile();
        if (!data.exists()) {
            try {
                data.createNewFile();
                //TODO figure out how to save this
            } catch (IOException exception) {
                Interconnect.LOGGER.error("Failed to save data for player '" + player.name() + "'", exception);
            }
        }
    }
}
