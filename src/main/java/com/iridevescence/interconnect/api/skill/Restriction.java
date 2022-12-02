package com.iridevescence.interconnect.api.skill;

import org.bukkit.entity.HumanEntity;

import net.kyori.adventure.util.TriState;

public record Restriction(String name, String requirement, String target, Type type) {
    public enum Type {
        BREAK_BLOCK,
        USE_ITEM,
        CRAFT_ITEM,
        EQUIP_ITEM
    }

    public boolean isUnlockedForPlayer(HumanEntity player) {
        return player.permissionValue(this.requirement) == TriState.TRUE;
    }
}
