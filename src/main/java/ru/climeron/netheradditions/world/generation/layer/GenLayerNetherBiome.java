package ru.climeron.netheradditions.world.generation.layer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import ru.climeron.netheradditions.main.Main;

public class GenLayerNetherBiome extends GenLayer
{
    public GenLayerNetherBiome(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] outputs = IntCache.getIntCache(areaWidth * areaHeight);

        for(int y = 0; y < areaHeight; y++)
        {
            for(int x = 0; x < areaWidth; x++)
            {
                this.initChunkSeed(x + areaX, y + areaY);
                outputs[x + y * areaWidth] = Biome.getIdForBiome(this.getRandomBiome());
            }
        }

        return outputs;
    }

    private Biome getRandomBiome()
    {
        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>(Main.BIOME_DATA_MANAGER.getWorldSpecificBiomeEntries().values());
        int biomeWeights = WeightedRandom.getTotalWeight(biomeEntries);
        return biomeWeights <= 0 ? Biomes.HELL : WeightedRandom.getRandomItem(biomeEntries, this.nextInt(biomeWeights)).biome;
    }
}