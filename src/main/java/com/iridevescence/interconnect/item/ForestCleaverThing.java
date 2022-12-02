package com.iridevescence.interconnect.item;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.iridevescence.interconnect.Interconnect;
import com.iridevescence.interconnect.item.impl.CustomItem;
import com.iridevescence.interconnect.util.block.BlockPosition;
import com.iridevescence.interconnect.util.block.TreeBreakerer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ForestCleaverThing implements CustomItem {


    public static final ForestCleaverThing INSTANCE = new ForestCleaverThing();

    @Override
    public Material baseItem() {
        return Material.STONE_AXE;
    }

    @Override
    public void processNew(ItemStack i) {
        i.editMeta((m) -> {
            m.displayName(Component.text("Forest Cleaver").decoration(TextDecoration.ITALIC, false));
            m.lore(List.of(
                Component.text("Very cool", NamedTextColor.GOLD),
                Component.text("lore..", NamedTextColor.GOLD)
            ));
        });
    }

    @Override
    public void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String name = block.getType().name();

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
            runnable.task = Interconnect.INSTANCE.getServer().getScheduler().runTaskTimer(Interconnect.INSTANCE, runnable, 0, 1); // every 1 tick(s)
        }
        
    }

    @Override
    public NamespacedKey identifier() {
        return new NamespacedKey(Interconnect.INSTANCE, "forestcleaver");
    }
    
}
