    package com.iridevescence.interconnect.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.CraftItemEvent;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;

import java.util.HashMap;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class InterconnectItemEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraftItem(CraftItemEvent event) {
        String itemName = event.getCurrentItem().getType().name();
        System.out.println(itemName);
        for (Restriction restriction : Registry.getRestrictionsByType(Restriction.Type.CRAFT_ITEM)) {
            System.out.println(restriction);
            if (restriction.target().equalsIgnoreCase(itemName) && !restriction.isUnlockedForPlayer(event.getWhoClicked())) {
                event.setCancelled(true);
                event.setResult(Result.DENY);
                if (event.getWhoClicked() instanceof Player player) {
                    player.sendActionBar(Component.text("You cannot craft " + itemName + " yet! You need the perk '" + Interconnect.TRANSLATIONS.getOrDefault("en_US", new HashMap<>()).getOrDefault(restriction.name() + ".", restriction.name() + ".")).color(TextColor.fromHexString("#CC0033")));
                }
                break;
            }
        }
    }
}
