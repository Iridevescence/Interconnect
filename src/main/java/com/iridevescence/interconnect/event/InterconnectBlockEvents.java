package com.iridevescence.interconnect.event;


import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class InterconnectBlockEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String name = block.getType().name();
        System.out.println(name);
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        for (Restriction restriction : Registry.getRestrictionsByType(Restriction.Type.BREAK_BLOCK)) {
            System.out.println(restriction);
            if (restriction.target().equalsIgnoreCase(name) && !restriction.isUnlockedForPlayer(player)) {
                event.setCancelled(true);
                player.sendActionBar(Component.text("You cannot break " + name + " yet! You need the perk '" + Interconnect.TRANSLATIONS.getOrDefault(player.locale().getLanguage(), new HashMap<>()).getOrDefault(restriction.name() + ".", restriction.name() + ".")).color(TextColor.fromHexString("#CC0033")));
                break;
            }
        }
        

        Interconnect.INSTANCE.customItemRegistry().getItem(heldItem).ifPresent((customItem) -> {
            customItem.blockBreakEvent(event);
        });
    }
}