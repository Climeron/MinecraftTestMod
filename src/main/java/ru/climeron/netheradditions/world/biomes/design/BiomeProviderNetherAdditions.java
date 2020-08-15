package ru.climeron.netheradditions.world.biomes.design;

import javax.annotation.Nullable;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import ru.climeron.netheradditions.world.generation.layer.GenLayerNetherBiome;
import ru.climeron.netheradditions.world.generation.layer.GenLayerNetherSubBiome;

public class BiomeProviderNetherAdditions extends BiomeProvider
{
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private BiomeProvider provider;
    
    public BiomeProviderNetherAdditions(World world)
    {
        super();
        GenLayer[] genLayers = this.createGenLayers(world.getWorldType(), world.getSeed());
        genBiomes = genLayers[0];
        biomeIndexLayer = genLayers[1];
        biomeCache = new BiomeCache(this);
    }
    
    public void BiomeCache(BiomeProvider provider)
    {
        this.provider = provider;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] biomeIds = this.genBiomes.getInts(x, z, width, height);

        try
        {
            for(int i = 0; i < width * height; i++)
            {
                biomes[i] = Biome.getBiome(biomeIds[i], Biomes.HELL);
            }

            return biomes;
        }
        catch(Throwable throwable)
        {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("RawBiomeBlock");
            crashReportCategory.addCrashSection("biomes[] size", biomes.length);
            crashReportCategory.addCrashSection("x", x);
            crashReportCategory.addCrashSection("z", z);
            crashReportCategory.addCrashSection("w", width);
            crashReportCategory.addCrashSection("h", height);
            throw new ReportedException(crashReport);
        }
    }
    
    @Override
    public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
    {
        return this.getBiomes(oldBiomeList, x, z, width, depth, true);
    }
    
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else
        {
            int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i)
            {
                listToReuse[i] = Biome.getBiome(aint[i], Biomes.HELL);
            }

            return listToReuse;
        }
    }

    private GenLayer[] createGenLayers(WorldType worldType, long worldSeed)
    {
        GenLayer baseLayer = new GenLayerIsland(1L);
        GenLayer biomeLayer = new GenLayerNetherBiome(200L, baseLayer);
        GenLayer subBiomeLayer = new GenLayerRiverInit(100L, biomeLayer);
        biomeLayer = GenLayerZoom.magnify(1000L, biomeLayer, 2);
        subBiomeLayer = GenLayerZoom.magnify(1000L, subBiomeLayer, 2);
        baseLayer = new GenLayerNetherSubBiome(1000L, biomeLayer, subBiomeLayer);
        baseLayer = GenLayerZoom.magnify(1000L, baseLayer, GenLayer.getModdedBiomeSize(worldType, 3));
        baseLayer = new GenLayerSmooth(1000L, baseLayer);
        GenLayer zoomedLayer = new GenLayerVoronoiZoom(10L, baseLayer);
        baseLayer.initWorldGenSeed(worldSeed);
        zoomedLayer.initWorldGenSeed(worldSeed);
        return new GenLayer[]{baseLayer, zoomedLayer};
    }
}
