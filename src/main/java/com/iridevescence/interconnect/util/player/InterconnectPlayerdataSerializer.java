package com.iridevescence.interconnect.util.player;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.util.StringStorage;

public class InterconnectPlayerdataSerializer {
    public static final NamespacedKey PERKS_UNLOCKED = new NamespacedKey(Interconnect.INSTANCE, "perks_unlocked");

    public static void serialize(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        int[] temp = container.getOrDefault(PERKS_UNLOCKED, PersistentDataType.INTEGER_ARRAY, new int[1]);
        ArrayList<Integer> perks = new ArrayList<>();
        for (int index : temp) {
            perks.add(index);
        }

        for (String perk : InterconnectPlayerdata.PERKS_UNLOCKED) {
            perks.add(StringStorage.getIndexOf(perk));
        }

        temp = new int[perks.size()];
        for (int index = 0; index < temp.length; index++) {
            temp[index] = perks.get(index);
        }

        container.set(PERKS_UNLOCKED, PersistentDataType.INTEGER_ARRAY, temp);
    }
}
