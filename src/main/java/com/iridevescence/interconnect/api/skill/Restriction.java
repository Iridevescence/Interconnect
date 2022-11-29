package com.iridevescence.interconnect.api.skill;

import org.bukkit.entity.Player;

public record Restriction(String name, String requirement, String target, Type type) {
    public enum Type {
        BREAK_BLOCK,
        USE_ITEM,
        CRAFT_ITEM,
        EQUIP_ITEM
    }

    public boolean isUnlockedForPlayer(Player player) {
        return false;
        //TODO actually check this
    }
}
