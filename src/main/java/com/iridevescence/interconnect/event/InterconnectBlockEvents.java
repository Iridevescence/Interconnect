package com.iridevescence.interconnect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.iridevescence.interconnect.api.skill.Registry;
import com.iridevescence.interconnect.api.skill.Restriction;

public class InterconnectBlockEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player breaker = event.getPlayer();
        String blockName = event.getBlock().getType().name();
        for (Restriction restriction : Registry.getRestrictionsByType(Restriction.Type.BREAK_BLOCK)) {
            if (restriction.target() == blockName && !restriction.isUnlockedForPlayer(breaker)) {
                event.setCancelled(true);
            }
        }
    }
}
