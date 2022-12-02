package com.iridevescence.interconnect.event;


import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.api.skill.Restriction;
import com.iridevescence.interconnect.util.Registry;
import com.iridevescence.interconnect.util.block.BlockPosition;
import com.iridevescence.interconnect.util.block.TreeBreakerer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static com.iridevescence.interconnect.Interconnect.YOURPLUGININSTANCEREPLACETHISNOWPRISMDOIT;



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

        if (heldItem.getType() == Material.STONE_AXE) {
            if (name.toLowerCase().contains("log")) {
                System.out.println("Running TreeBreakerer!!");
                World world = player.getWorld();
                Set<BlockPosition> positions = new HashSet<>();

                Queue<List<BlockPosition>> posToDos = new ArrayDeque<>();
                TreeBreakerer.process(world, positions, new BlockPosition(block.getX(), block.getY(), block.getZ()), posToDos::add);

                class BreakererTask implements Runnable {
                    public BukkitTask task;

                    @Override
                    public void run() {
                        int numToDo = Math.min(8, Math.max(1, posToDos.size() / 6));

                        for (int i = 0; i < numToDo; i++) {
                            if (posToDos.isEmpty()) {
                                task.cancel();
                                return;
                            }
    

                            List<BlockPosition> positionList = posToDos.remove();

                            for (BlockPosition position : positionList) {
                                Location location = new Location(world, position.x(), position.y(), position.z());
                                BlockData data = world.getBlockAt(location).getBlockData();
                                world.spawnParticle(Particle.BLOCK_CRACK, location.add(0.5, 0.5, 0.5), 25, data);
                                ItemStack stack = new ItemStack(world.getBlockAt(location).getType());
                                world.getBlockAt(location).setType(Material.AIR);
                                world.playSound(location, data.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 1f, 1f);
                                world.dropItem(location, stack);
                            }
                        }

                    }
                }
                BreakererTask runnable = new BreakererTask();
                runnable.task = YOURPLUGININSTANCEREPLACETHISNOWPRISMDOIT.getServer().getScheduler().runTaskTimer(YOURPLUGININSTANCEREPLACETHISNOWPRISMDOIT, runnable, 0, 1); // every 1 tick(s)
            }
        }
    }
}