package ru.climeron.netheradditions.world.biomes.data;

import net.minecraft.util.ResourceLocation;
import ru.climeron.netheradditions.main.Main;

public class BiomeDataBOP extends BiomeData
{
    public BiomeDataBOP(ResourceLocation registryName, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        super(registryName, generationWeight, useDefaultDecorations, isSubBiome);
    }

    @Override
    public boolean isEnabled()
    {
        return super.isEnabled() && Main.BIOMES_O_PLENTY_LOADED;
    }
}
