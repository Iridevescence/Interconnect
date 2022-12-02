package com.iridevescence.interconnect.util.block;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

record Entry(BlockPosition position, int power) {}

public class TreeBreakerer {

    protected static final int STARTING_POWER = 16;

    protected final Queue<Entry> processingQueue = new ArrayDeque<>();

    protected void doProcessing(final World world, final Set<BlockPosition> output, Consumer<List<BlockPosition>> onRun) {
        while (!this.processingQueue.isEmpty()) {
            Entry item = this.processingQueue.remove();
            System.out.println("TreeBreakerer is processing: " + item);
            if (item.power() == 0 || output.contains(item.position())) {
                continue;
            }
            output.add(item.position());

            List<BlockPosition> batchValues = new ArrayList<>();
            for (BlockPosition pos : item.position().blocksOnAllSidesOf()) {
                String name = world.getBlockAt(new Location(world, pos.x(), pos.y(), pos.z())).getType().name().toLowerCase();
                if (name.contains("log")) { // || name.contains("leaves")
                    if (output.contains(pos)) {
                        continue;
                    }
                    this.processingQueue.add(new Entry(pos, item.power() - 1));
                    batchValues.add(pos);
                }
            }
            onRun.accept(batchValues);
        }
    }

    public static void process(World world, Set<BlockPosition> output, BlockPosition startingBlock, Consumer<List<BlockPosition>> onRun) {
        TreeBreakerer breakerer = new TreeBreakerer();
        onRun.accept(List.of(startingBlock));
        breakerer.processingQueue.add(new Entry(startingBlock, STARTING_POWER));
        breakerer.doProcessing(world, output, onRun);
    }
}