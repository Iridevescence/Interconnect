package com.iridevescence.interconnect.item.impl;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomItem {


    Material baseItem();

    void processNew(ItemStack i);

    NamespacedKey identifier();

    void blockBreakEvent(BlockBreakEvent event);

}
