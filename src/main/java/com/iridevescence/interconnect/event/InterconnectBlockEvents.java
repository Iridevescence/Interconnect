package com.iridevescence.interconnect.event;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class InterconnectBlockEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player breaker = event.getPlayer();
        String blockName = event.getBlock().getType().name();
        for (Restriction restriction : Registry.getRestrictionsByType(Restriction.Type.BREAK_BLOCK)) {
            if (restriction.target() == blockName && !restriction.isUnlockedForPlayer(breaker)) {
                event.setCancelled(true);
                breaker.sendActionBar(Component.text("You cannot break " + blockName + " yet! You need the perk '" + Interconnect.TRANSLATIONS.getOrDefault(breaker.locale().getLanguage(), Interconnect.TRANSLATIONS.get("en_US")).get(restriction.name() + ".")).color(TextColor.fromHexString("#CC0033")));
            }
        }
    }
}
