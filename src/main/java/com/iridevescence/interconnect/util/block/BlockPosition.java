package com.iridevescence.interconnect.util.block;

public record BlockPosition(int x, int y, int z) {
    public BlockPosition[] blocksOnAllSidesOf() {
        return new BlockPosition[] {
                new BlockPosition(x - 1, y, z),
                new BlockPosition(x, y - 1, z),
                new BlockPosition(x, y, z - 1),
                new BlockPosition(x + 1, y, z),
                new BlockPosition(x, y + 1, z),
                new BlockPosition(x, y, z + 1),
        };
    }
}
