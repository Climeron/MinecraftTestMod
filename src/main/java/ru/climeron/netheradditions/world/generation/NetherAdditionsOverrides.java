package ru.climeron.netheradditions.world.generation;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import ru.climeron.netheradditions.world.design.WorldProviderNetherAdditions;

public class NetherAdditionsOverrides
{
    public static void overrideObjects()
    {
        Biomes.HELL.topBlock = Blocks.NETHERRACK.getDefaultState();
        Biomes.HELL.fillerBlock = Blocks.NETHERRACK.getDefaultState();
    }

    public static void overrideNether()
    {
    	DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNetherAdditions.class, false);
        DimensionManager.registerDimension(-1, nether);
    }
}