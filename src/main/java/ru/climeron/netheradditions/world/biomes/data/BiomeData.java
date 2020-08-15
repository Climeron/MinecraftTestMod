package ru.climeron.netheradditions.world.biomes.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.climeron.netheradditions.world.generation.GenerationStage;
import ru.climeron.netheradditions.world.generation.traits.BiomeTrait;

public class BiomeData
{
    protected final Biome biome;
    protected int generationWeight;
    protected boolean useDefaultDecorations;
    protected boolean isSubBiome;
    protected final Map<BlockType, IBlockState> biomeBlocks;
    protected final Map<GenerationStage, List<BiomeTrait>> biomeTraits;
    protected final Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawns;
    protected final List<BiomeData> subBiomes;

    public BiomeData(Biome biome, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        if(biome != null)
        {
            this.biome = biome;
        }
        else
        {
            this.biome = Biomes.PLAINS;
        }

        this.generationWeight = generationWeight;
        this.useDefaultDecorations = useDefaultDecorations;
        this.isSubBiome = isSubBiome;
        this.biomeBlocks = new EnumMap<>(BlockType.class);
        this.biomeTraits = new EnumMap<>(GenerationStage.class);
        this.entitySpawns = new EnumMap<>(EnumCreatureType.class);
        this.subBiomes = new ArrayList<>();
    }

    public BiomeData(ResourceLocation biomeRegistryName, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        this(ForgeRegistries.BIOMES.getValue(biomeRegistryName), generationWeight, useDefaultDecorations, isSubBiome);
    }

    public void addBiomeBlock(BlockType blockType, IBlockState state)
    {
        this.biomeBlocks.put(blockType, state);
    }

    public void addEntitySpawn(EnumCreatureType creatureType, Biome.SpawnListEntry spawnListEntry)
    {
        if(spawnListEntry.itemWeight > 0)
        {
            this.entitySpawns.computeIfAbsent(creatureType, k -> new ArrayList<>()).add(spawnListEntry);
        }

        for(EnumCreatureType type : EnumCreatureType.values())
        {
            this.biome.getSpawnableList(type).removeIf(entry -> entry.entityClass == spawnListEntry.entityClass);
        }
    }

    public void addBiomeTrait(GenerationStage generationStage, BiomeTrait biomeTrait)
    {
        this.biomeTraits.computeIfAbsent(generationStage, k -> new ArrayList<>()).add(biomeTrait);
    }

    public void addSubBiome(BiomeData biomeData)
    {
        this.subBiomes.add(biomeData);
    }

    public Biome getBiome()
    {
        return this.biome;
    }

    public int getGenerationWeight()
    {
        return this.generationWeight;
    }

    public boolean useDefaultBiomeDecorations()
    {
        return this.useDefaultDecorations;
    }

    public boolean isSubBiome()
    {
        return this.isSubBiome;
    }

    public boolean isEnabled()
    {
        return this.generationWeight > 0;
    }

    public IBlockState getBiomeBlock(BlockType biomeBlock)
    {
        return this.biomeBlocks.get(biomeBlock);
    }

    public Map<BlockType, IBlockState> getBiomeBlocks()
    {
        return this.biomeBlocks;
    }

    public List<BiomeTrait> getBiomeTraits(GenerationStage generationStage)
    {
        return this.biomeTraits.computeIfAbsent(generationStage, k -> new ArrayList<>());
    }

    public List<Biome.SpawnListEntry> getEntitySpawns(EnumCreatureType creatureType)
    {
        return this.entitySpawns.computeIfAbsent(creatureType, k -> new ArrayList<>());
    }

    public List<BiomeData> getSubBiomes()
    {
        return this.subBiomes;
    }

    public enum BlockType
    {
        SURFACE_BLOCK("surface"),
        SUBSURFACE_BLOCK("subsurface"),
        LIQUID_BLOCK("liquid");

        private String identifier;

        BlockType(String identifier)
        {
            this.identifier = identifier;
        }

        public static BlockType getFromIdentifier(String identifier)
        {
            for(BlockType type : BlockType.values())
            {
                if(type.toString().equals(identifier))
                {
                    return type;
                }
            }

            return SURFACE_BLOCK;
        }

        @Override
        public String toString()
        {
            return this.identifier;
        }
    }
}
