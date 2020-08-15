package ru.climeron.netheradditions.world.generation.traits;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import ru.climeron.netheradditions.main.Main;

public final class BiomeTraitRegistry
{
    public static final BiomeTraitRegistry INSTANCE = new BiomeTraitRegistry();

    private final Map<Class<? extends BiomeTrait>, ResourceLocation> biomeTraitNames = new HashMap<>();
    private final Map<ResourceLocation, BiomeTrait.Builder<?>> biomeTraitBuilders = new HashMap<>();

    private BiomeTraitRegistry()
    {
        this.registerBiomeTrait(Main.getResource("scatter"), new BiomeTraitScatter.Builder(), BiomeTraitScatter.class);
        this.registerBiomeTrait(Main.getResource("cluster"), new BiomeTraitCluster.Builder(), BiomeTraitCluster.class);
        this.registerBiomeTrait(Main.getResource("patch"), new BiomeTraitPatch.Builder(), BiomeTraitPatch.class);
        this.registerBiomeTrait(Main.getResource("boulder"), new BiomeTraitBoulder.Builder(), BiomeTraitBoulder.class);
        this.registerBiomeTrait(Main.getResource("ore"), new BiomeTraitOre.Builder(), BiomeTraitOre.class);
        this.registerBiomeTrait(Main.getResource("fluid"), new BiomeTraitFluid.Builder(), BiomeTraitFluid.class);
        this.registerBiomeTrait(Main.getResource("pool"), new BiomeTraitPool.Builder(), BiomeTraitPool.class);
        this.registerBiomeTrait(Main.getResource("basic_tree"), new BiomeTraitCrimsonTree.Builder(), BiomeTraitCrimsonTree.class);
        this.registerBiomeTrait(Main.getResource("dense_tree"), new BiomeTraitDenseTree.Builder(), BiomeTraitDenseTree.class);
        this.registerBiomeTrait(Main.getResource("sparse_tree"), new BiomeTraitSparseTree.Builder(), BiomeTraitSparseTree.class);
        this.registerBiomeTrait(Main.getResource("big_mushroom"), new BiomeTraitBigMushroom.Builder(), BiomeTraitBigMushroom.class);
        this.registerBiomeTrait(Main.getResource("structure"), new BiomeTraitStructure.Builder(), BiomeTraitStructure.class);
        this.registerBiomeTrait(Main.getResource("crimson_tree"), new BiomeTraitCrimsonTree.Builder(), BiomeTraitCrimsonTree.class);
        this.registerBiomeTrait(Main.getResource("fungi"), new BiomeTraitFungi.Builder(), BiomeTraitFungi.class);
        this.registerBiomeTrait(Main.getResource("nether_sprouts"), new BiomeTraitNetherSprouts.Builder(), BiomeTraitNetherSprouts.class);
        this.registerBiomeTrait(Main.getResource("plants"), new BiomeTraitPlants.Builder(), BiomeTraitPlants.class);
        this.registerBiomeTrait(Main.getResource("twisting_vines"), new BiomeTraitTwistingVines.Builder(), BiomeTraitTwistingVines.class);
        this.registerBiomeTrait(Main.getResource("warped_tree"), new BiomeTraitWarpedTree.Builder(), BiomeTraitWarpedTree.class);
        this.registerBiomeTrait(Main.getResource("fossil"), new BiomeTraitFossil.Builder(), BiomeTraitFossil.class);
    }

    public void registerBiomeTrait(ResourceLocation registryName, BiomeTrait.Builder<?> biomeTraitBuilder, Class<? extends BiomeTrait> cls)
    {
        if(registryName == null || biomeTraitBuilder == null || cls == null)
        {
            return;
        }

        if(!this.biomeTraitNames.containsKey(cls))
        {
            this.biomeTraitNames.put(cls, registryName);
        }

        if(!this.biomeTraitBuilders.containsKey(registryName))
        {
            this.biomeTraitBuilders.put(registryName, biomeTraitBuilder);
        }
    }

    public void unregisterBiomeTrait(ResourceLocation registryName)
    {
        this.biomeTraitBuilders.remove(registryName);
    }

    public boolean hasBiomeTrait(ResourceLocation registryName)
    {
        return this.biomeTraitBuilders.containsKey(registryName);
    }

    public ResourceLocation getBiomeTraitName(Class<? extends BiomeTrait> cls)
    {
        return this.biomeTraitNames.get(cls);
    }

    public BiomeTrait.Builder<?> getBiomeTraitBuilder(ResourceLocation registryName)
    {
        return this.biomeTraitBuilders.get(registryName);
    }

    public Map<Class<? extends BiomeTrait>, ResourceLocation> getBiomeTraitNames()
    {
        return Collections.unmodifiableMap(this.biomeTraitNames);
    }

    public Map<ResourceLocation, BiomeTrait.Builder<?>> getBiomeTraitBuilders()
    {
        return Collections.unmodifiableMap(this.biomeTraitBuilders);
    }
}
