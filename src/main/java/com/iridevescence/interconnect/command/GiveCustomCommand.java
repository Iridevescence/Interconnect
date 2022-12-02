package com.iridevescence.interconnect.command;

import java.util.Optional;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.item.impl.CustomItem;

public class GiveCustomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2,
            @NotNull String[] arg3) {
        if (sender.isOp() && sender instanceof Player player) {
            if (arg3.length < 1) {
                return false;
            } 
            NamespacedKey key = NamespacedKey.fromString(arg3[0]);
            if (key == null) {
                sender.sendMessage("Failed to parse NamespacedKey from \"" + arg3[0] + "\"");
                return false;
            }
            Optional<CustomItem> item = Interconnect.INSTANCE.customItemRegistry().getItemByKey(key);
            if (item.isPresent()) {
                CustomItem customItem = item.get();
                ItemStack instance = Interconnect.INSTANCE.customItemRegistry().createInstanceOf(customItem);
                player.getInventory().addItem(instance);
            } else {
                sender.sendMessage("Could not find any item by key: " + key.asString());
                return false;
            }
            return true;
        }
        return false;
    }
    
}
