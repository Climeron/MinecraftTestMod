package ru.climeron.netheradditions.world.biomes.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeDataManager
{
    private final String modId;
    private final Logger logger;
    private final Map<ResourceLocation, BiomeData> defaultBiomeData;
    private final Map<ResourceLocation, BiomeData> worldSpecificBiomeData;
    private final Map<ResourceLocation, BiomeManager.BiomeEntry> WorldSpecificBiomeEntries;

    public BiomeDataManager(String modId, String modName)
    {
        this.modId = modId;
        this.logger = LogManager.getLogger(modName);
        this.defaultBiomeData = new HashMap<>();
        this.worldSpecificBiomeData = new HashMap<>();
        this.WorldSpecificBiomeEntries = new ConcurrentHashMap<>();
    }

    public void setup()
    {
        this.worldSpecificBiomeData.forEach(this.defaultBiomeData::put);
    }

    public void registerBiomeData(BiomeData biomeData)
    {
        if(biomeData != null && biomeData.getBiome() != null)
        {
            Biome biome = biomeData.getBiome();
            ResourceLocation biomeRegistryName = biome.getRegistryName();

            if(!this.worldSpecificBiomeData.containsKey(biomeRegistryName))
            {
                this.worldSpecificBiomeData.put(biomeRegistryName, biomeData);
            }
            if(!biomeData.isSubBiome())
            {
                if(biomeData.isEnabled())
                {
                    this.WorldSpecificBiomeEntries.put(biomeRegistryName, new BiomeManager.BiomeEntry(biome, biomeData.getGenerationWeight()));
                }
                else
                {
                    this.WorldSpecificBiomeEntries.remove(biomeRegistryName);
                }
            }
        }
    }

    public void unregisterBiomeData(Biome biome)
    {
        if(biome != null)
        {
            ResourceLocation biomeRegistryName = biome.getRegistryName();
            this.worldSpecificBiomeData.remove(biomeRegistryName);
            this.WorldSpecificBiomeEntries.remove(biomeRegistryName);
        }
    }

    public void cleanup(WorldEvent.Unload event)
    {
        this.worldSpecificBiomeData.clear();
        this.WorldSpecificBiomeEntries.clear();
    }

    public boolean hasBiomeData(Biome biome)
    {
        return this.worldSpecificBiomeData.containsKey(biome.getRegistryName());
    }

    public BiomeData getBiomeData(Biome biome)
    {
        return this.worldSpecificBiomeData.get(biome.getRegistryName());
    }

    public Map<ResourceLocation, BiomeData> getDefaultBiomeData()
    {
        return Collections.unmodifiableMap(this.defaultBiomeData);
    }


    public Map<ResourceLocation, BiomeData> getWorldSpecificBiomeData()
    {
        return Collections.unmodifiableMap(this.worldSpecificBiomeData);
    }

    public Map<ResourceLocation, BiomeManager.BiomeEntry> getWorldSpecificBiomeEntries()
    {
        return Collections.unmodifiableMap(this.WorldSpecificBiomeEntries);
    }
}
