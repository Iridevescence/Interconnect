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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class InterconnectItemEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraftItem(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            String itemName = event.getCurrentItem().getType().name();
            for (Restriction restriction : Registry.getRestrictionsByType(Restriction.Type.CRAFT_ITEM)) {
                if (restriction.target() == itemName && !restriction.isUnlockedForPlayer(player)) {
                    event.setCancelled(true);
                    event.setResult(Result.DENY);
                    player.sendActionBar(Component.text("You cannot craft " + itemName + " yet! You need the perk '" + Interconnect.TRANSLATIONS.getOrDefault(player.locale().getLanguage(), Interconnect.TRANSLATIONS.get("en_US")).get(restriction.name() + ".")).color(TextColor.fromHexString("#CC0033")));
                    break;
                }
            }
        }
    }
}
