package ru.climeron.netheradditions.world.biomes;

import net.minecraft.world.biome.Biome;
import ru.climeron.netheradditions.reference.Reference;
import ru.climeron.netheradditions.world.biomes.data.BiomeData;

public abstract class BiomeMod<T extends BiomeData> extends Biome
{
    public BiomeMod(BiomeProperties properties, String name)
    {
        super(properties);
        this.setRegistryName(Reference.MODID + ":" + name);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    public abstract T getBiomeData();
}
