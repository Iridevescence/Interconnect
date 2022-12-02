package com.iridevescence.interconnect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;

import java.util.HashMap;

public class RestrictionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
        if (arg3.length >= 1) {
            switch (arg3[0].toLowerCase()) {
                case "list" -> {
                    String type = "all";
                    if (arg3.length >= 2) {
                        switch (arg3[1].toLowerCase()) {
                            case "use":
                            case "break":
                            case "craft":
                            case "equip":
                                type = arg3[1].toLowerCase();
                                break;
                            default:
                                type = "invalid";
                        }
                    }
                    switch (type) {
                        case "all" -> {
                            for (Restriction restriction : Registry.getAllRestrictions()) {
                                String locale = "en_US";
                                if (arg0 instanceof Player player) {
                                    locale = player.locale().getLanguage();
                                }
                                arg0.sendMessage(Interconnect.TRANSLATIONS.getOrDefault(locale, new HashMap<>()).getOrDefault(restriction.name(), restriction.name()));
                            }
                            return true;
                        }
                        case "invalid" -> {
                            return false;
                        }
                    }
                }
                case "add" -> {
                    if (arg3.length >= 5) {
                        String type = "invalid";
                        switch (arg3[1].toLowerCase()) {
                            case "use":
                            case "break":
                            case "craft":
                            case "equip":
                                type = arg3[1].toLowerCase();
                                break;
                        }

                        // args: type, name, requirement, target
                        switch (type) {
                            case "break" -> {
                                Registry.addRestriction(new Restriction(arg3[2], arg3[3], arg3[4], Restriction.Type.BREAK_BLOCK));
                            }
                            case "craft" -> {
                                Registry.addRestriction(new Restriction(arg3[2], arg3[3], arg3[4], Restriction.Type.CRAFT_ITEM));
                            }
                            case "invalid" -> {
                                return false;
                            }
                        }
                    }
                }
                case "remove" -> {
                }
            }
        }
        return false;
    }
}
