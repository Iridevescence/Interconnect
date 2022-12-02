package com.iridevescence.interconnect.item.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import com.iridevescence.interconnect.Interconnect;

public class CustomItemRegistry {
    protected Map<NamespacedKey, CustomItem> registry = new HashMap<>();

    protected static final NamespacedKey CUSTOM_DATA_TYPE_KEY = Interconnect.newKey("custom_data_type");

    public void registerItem(CustomItem i) {
        this.registry.put(i.identifier(), i);
    }

    public Optional<CustomItem> getItemByKey(NamespacedKey k) {
        return Optional.ofNullable(this.registry.get(k));
    }

    public Optional<CustomItem> getItem(ItemStack k) {
        return Optional.ofNullable(
                k.getItemMeta()
                .getPersistentDataContainer()
                .get(CUSTOM_DATA_TYPE_KEY, PersistentDataType.STRING))
                .map((v) -> NamespacedKey.fromString(v))
                .flatMap((v) -> this.getItemByKey(v));
    }


    public ItemStack createInstanceOf(CustomItem item) {
        ItemStack i = new ItemStack(item.baseItem());
        item.processNew(i);
        i.editMeta((m) -> {
            m.getPersistentDataContainer().set(
                CUSTOM_DATA_TYPE_KEY, PersistentDataType.STRING,
                item.identifier().asString());
        });
        return i;
    }

    public Optional<ItemStack> createInstanceOf(NamespacedKey item) {
        return this.getItemByKey(item).map((v) -> createInstanceOf(v));
    }
}
