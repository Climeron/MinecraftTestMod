package ru.climeron.netheradditions.event;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;

public class ChunkGenerateEvent extends ChunkEvent
{
    public ChunkGenerateEvent(Chunk chunk)
    {
        super(chunk);
    }
}
