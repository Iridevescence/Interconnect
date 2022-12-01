package com.iridevescence.interconnect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;

public class RestrictionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
        switch (arg2.toLowerCase()) {
            case "list" -> {
                String type = "all";
                if (arg3.length >= 1) {
                    switch (arg3[0].toLowerCase()) {
                        case "use":
                        case "break":
                        case "craft":
                        case "equip":
                            type = arg3[0].toLowerCase();
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
                            arg0.sendMessage(Interconnect.TRANSLATIONS.get(locale).get(restriction.name()));
                        }
                    }
                }
            }
            case "add" -> {
                if (arg3.length >= 5) {
                    String type = "invalid";
                    switch (arg3[0].toLowerCase()) {
                        case "use":
                        case "break":
                        case "craft":
                        case "equip":
                            type = arg3[0].toLowerCase();
                            break;
                    }
                    switch (type) {
                        case "break" -> {
                            // args: type, name, requirement, target
                            Registry.addRestriction(new Restriction(arg3[1], arg3[2], arg3[3], Restriction.Type.BREAK_BLOCK));
                        }
                    }
                }
            }
            case "remove" -> {

            }
        }
        return false;
    }
    
}
